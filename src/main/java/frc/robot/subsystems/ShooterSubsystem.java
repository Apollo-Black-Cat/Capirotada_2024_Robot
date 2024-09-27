// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  private final CANSparkMax leftMotor;
  private final CANSparkMax rightMotor;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    leftMotor = new CANSparkMax(ShooterConstants.KShooterLeftMotor, MotorType.kBrushless);
    rightMotor = new CANSparkMax(ShooterConstants.KShooterRightMotor, MotorType.kBrushless);
    rightMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void ActivateShooter(boolean shoot) {
    if (shoot) {
      leftMotor.set(ShooterConstants.KShooterMotorSpeed);
      rightMotor.set(ShooterConstants.KShooterMotorSpeed);
    } else {
      leftMotor.set(-ShooterConstants.KShooterMotorSpeed);
      rightMotor.set(-ShooterConstants.KShooterMotorSpeed);
    }
  }

  public void SetMotorsSpeed(double leftMotorSpeed, double rightMotorSpeed) {
    leftMotor.set(leftMotorSpeed);
    rightMotor.set(rightMotorSpeed);
  }

  public void stopMotors() {
    leftMotor.stopMotor();
    rightMotor.stopMotor();
  }
}
