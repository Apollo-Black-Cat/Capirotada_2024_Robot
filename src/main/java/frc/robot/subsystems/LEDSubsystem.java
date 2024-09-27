// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
  /** Creates a new LEDSubsystem. */
  private final AddressableLED Led = new AddressableLED(7);
  private final AddressableLEDBuffer m_LedBuffer = new AddressableLEDBuffer(210);
  //Ajuste por si se requiere desactivar las animaciones LED.
  private Boolean ActivateLEDs = true;
  public static int j = 1, position;

  
  public LEDSubsystem() {
    ledInit(1);
  }

  public void ledInit(int mode) {
    Led.setLength(m_LedBuffer.getLength());
    Led.setData(m_LedBuffer);
    Led.start();
    if (ActivateLEDs == false) {
      return;
    }
    if (mode == 1) {
      Solid(11, 255, 255);
    }
    if (mode == 2) {
      RunningLights(0, 100, 255, true);
    }
    if (mode == 3) {
      Strobe(50, 255, 255, 0.020);
    }
    if (mode == 4) {
      Strobe(0, 255, 255, 0.020);
    }
    if (mode == 5) {
      Solid(250, 70, 100);
    }
    if (mode == 6) {
      RunningLights(255, 50, 255, false);
    }
    Start();
  }

  public void Rainbow() {
    int m_rainbowFirstPixelHue = 0;
    for (int i = 0; i < m_LedBuffer.getLength(); i++) {
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_LedBuffer.getLength()) % 180);
      m_LedBuffer.setHSV(i, hue, 255, 128);
    }
    m_rainbowFirstPixelHue += 3;
    m_rainbowFirstPixelHue %= 180;
  }

  public void Solid(int hue, int saturation, int value) {
    for (int i = 0; i < m_LedBuffer.getLength(); i++) {
      m_LedBuffer.setHSV(i, hue, saturation, value);
    }
  }

  public void Strobe(int hue, int saturation, int value, double delay) {
    for (int i = 0; i < m_LedBuffer.getLength(); i++) {
      m_LedBuffer.setHSV(i, hue, saturation, value);
    }
    Start();
    Timer.delay(delay);
    for (int i = 0; i < m_LedBuffer.getLength(); i++) {
      m_LedBuffer.setRGB(i, 0, 0, 0);
    }
    Start();
    Timer.delay(delay);
  }

  public void RunningLights(int red, int green, int blue, Boolean isShooting) {
    if (isShooting) {
      position++;
    }
    else {
      position--;
    }
    for (int i = 0;  i< m_LedBuffer.getLength(); i++) {
      double r = ((Math.sin(i+position) + 127 + 128) / 255);
      double g = ((Math.sin(i+position) + 127 + 128) / 255);
      double b = ((Math.sin(i+position) + 127 + 128) / 255);
      int r1 = (int)r;
      int g1 = (int)g;
      int b1 = (int)b;
      m_LedBuffer.setRGB(i, r1 * red, g1 * green, b1 * blue);
      }
      Start();
      Timer.delay(0.005);
  }

  public void RainbowCycle(double delayDuration) {
    for (int i = 0; i < m_LedBuffer.getLength(); i++) {
      int[] c = Wheel((i * 256 / m_LedBuffer.getLength() + j) & 255);
      m_LedBuffer.setRGB(i, c[0], c[1], c[2]);
    }
    j++;
    Start();
    Timer.delay(delayDuration);
  }

  //Utilizado por el método "RainbowCycle".
  public int[] Wheel(int wheelposition) {
    int[] c = new int[3];
    if (wheelposition < 85) {
      c[0] = wheelposition * 3;
      c[1] = 255 - wheelposition * 3;
      c[2] = 0;
    }
    else if (wheelposition < 170) {
      wheelposition -= 85;
      c[0] = 255 - wheelposition * 3;
      c[1] = 0;
      c[2] = wheelposition * 3;
    }
    else {
      c[0] = 0;
      c[1] = wheelposition * 3;
      c[2] = 255 - wheelposition * 3;
    }
    return c;
  }

  public void Start() {
    Led.setLength(m_LedBuffer.getLength());
    Led.setData(m_LedBuffer);
    Led.start();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
