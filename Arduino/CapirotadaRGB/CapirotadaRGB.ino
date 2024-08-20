#include <Adafruit_NeoPixel.h>
#define NUM_LEDS 
#define PIN 
int mode = 1;
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, PIN, NEO_GRB + NEO_KHZ800);
void setup() {
  strip.begin();
  strip.show();
  Serial.begin(9600);
}

void loop() {
  if(SSerial.available() > 0) {
    string comando = Serial.readStringUntil('\n');
    if (comando == "intakeIn") {
      Strobe(0x00,0xFF,0x00, 10, 50, 0);
    }
    if (comando == "intakeOut") {
      Strobe(0xFF,0x00,0x00, 10, 50, 0);
    }
    if (comando == "shooter") {
      RunningLights(0xFF,0x80,0x00, 50);
      RunningLights(0xFF,0x80,0x00, 50);
      RunningLights(0x80,0x00,0x00, 50);
    }
    if (comando == "enable") {
      RunningLightsRainbow(25);
    }
    if (comando == "climberOn") {
      colorWipe(0x00,0x00,0xFF, 50);
      colorWipe(0x00,0x00,0x00, 50);
    }
    if (comando == "shooterSlow") {
      colorWipe(0xFF,0x80,0x00, 100);
      colorWipe(0x00,0x00,0x00, 100);
    }
    if (comando == "chill") {
      if (mode == 1){
  	    rainbowCycle(20);
        for (int i = 0; i < NUM_LEDS; i++){
          setPixel(i, 0, 0, 0);
        }
        strip.show();
        mode = random(1, 3);
      }
      if (mode == 2){
  	    Strobe(0xFF,0x00,0x00, 20, 100, 0);
        for (int i = 0; i < NUM_LEDS; i++){
          setPixel(i, 0, 0, 0);
        }
        strip.show();
        mode = random(1, 3);
      }
      if (mode == 3){
  	    colorWipeRainbow(0x00,0xff,0x00, 25);
        colorWipeRainbow(0x00,0x00,0x00, 25);
        for (int i = 0; i < NUM_LEDS; i++){
          setPixel(i, 0, 0, 0);
        }
        strip.show();
        mode = random(1, 3);
      }
    }
  }
}

void Strobe(byte red, byte green, byte blue, int StrobeCount, int FlashDelay, int EndPause){
  byte *c;
  uint16_t i, j;
  for(int j = 0; j < StrobeCount; j++) {
    if (mode == 2) {
      for(int i = 0; i < NUM_LEDS; i++ ) {
    	c = Wheel(((i * 256 / NUM_LEDS) + j) & 255);
    	setPixel(i, *c, *(c+1), *(c+2)); 
  	  }
    }
    else {
      setAll(red, green, blue)
    }
    showStrip();
    delay(FlashDelay);
    setAll(0,0,0);
    showStrip();
    delay(FlashDelay);
  }
 
 delay(EndPause);
}

void colorWipe(byte red, byte green, byte blue, int SpeedDelay) {
  for(uint16_t i=0; i<NUM_LEDS; i++) {
      setPixel(i, red, green, blue);
      showStrip();
      delay(SpeedDelay);
  }
}

void colorWipeRainbow(byte red, byte green, byte blue, int SpeedDelay) {
  byte *c;
  uint16_t i, j;
  if (red == 0x00 && green == 0x00 && blue == 0x00){
  	for(uint16_t i=0; i<NUM_LEDS; i++) {
      setPixel(i, red, green, blue);
      showStrip();
      delay(SpeedDelay);
    }
  }
  else{
    for(uint16_t i=0; i<NUM_LEDS; i++) {
      c=Wheel(((i * 256 / NUM_LEDS) + j) & 255);
      setPixel(i, *c, *(c+1), *(c+2));
      showStrip();
      delay(SpeedDelay);
    }
  }
}

void rainbowCycle(int SpeedDelay) {
  byte *c;
  uint16_t i, j;

  for(j=0; j<200; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< NUM_LEDS; i++) {
      c=Wheel(((i * 256 / NUM_LEDS) + j) & 255);
      setPixel(i, *c, *(c+1), *(c+2));
    }
    showStrip();
    delay(SpeedDelay);
  }
}

void RunningLightsRainbow(int WaveDelay) {
  int Position=0; 
  byte *c;
  uint16_t i, j;
  for(int i=0; i<NUM_LEDS*2; i++)
  {
      Position++; // = 0; //Position + Rate;
      for(int i=0; i<NUM_LEDS; i++) {      
        c=Wheel(((i * 256 / NUM_LEDS) + j) & 255);
        setPixel(i,((sin(i+Position) * 127 + 128)/255)*(*c),
                   ((sin(i+Position) * 127 + 128)/255)*(*(c+1)),
                   ((sin(i+Position) * 127 + 128)/255)*(*(c+2)));
      }
      
      showStrip();
      delay(WaveDelay);
  }
}

void RunningLights(byte red, byte green, byte blue, int WaveDelay) {
  int Position=0; 
  for(int i=0; i<NUM_LEDS*2; i++)
  {
      Position--; // = 0; //Position + Rate;
      for(int i=0; i<NUM_LEDS; i++) {      
        setPixel(i,((sin(i+Position) * 127 + 128)/255)*red,
                   ((sin(i+Position) * 127 + 128)/255)*green,
                   ((sin(i+Position) * 127 + 128)/255)*blue);
      }
      
      showStrip();
      delay(WaveDelay);
  }
}

byte * Wheel(byte WheelPos) {
  static byte c[3];
  
  if(WheelPos < 85) {
   c[0]=WheelPos * 3;
   c[1]=255 - WheelPos * 3;
   c[2]=0;
  } else if(WheelPos < 170) {
   WheelPos -= 85;
   c[0]=255 - WheelPos * 3;
   c[1]=0;
   c[2]=WheelPos * 3;
  } else {
   WheelPos -= 170;
   c[0]=0;
   c[1]=WheelPos * 3;
   c[2]=255 - WheelPos * 3;
  }

  return c;
}

void showStrip() {
   strip.show();
}

void setPixel(int Pixel, byte red, byte green, byte blue) {
   strip.setPixelColor(Pixel, strip.Color(red, green, blue));
}

void setAll(byte red, byte green, byte blue) {
  for(int i = 0; i < NUM_LEDS; i++ ) {
    setPixel(i, red, green, blue); 
  }
  showStrip();
}