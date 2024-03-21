// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */
  Solenoid climberSolenoidLeft;
  Solenoid climberSolenoidRight;

  public ClimberSubsystem() {
    climberSolenoidLeft = new Solenoid(PneumaticsModuleType.CTREPCM, ClimberConstants.KClimberSolenoidLeft);
    climberSolenoidRight = new Solenoid(PneumaticsModuleType.CTREPCM, ClimberConstants.KClimberSolenoidRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Escalador", climberSolenoidLeft.get());
  }

  public void toogleClimber() {
    climberSolenoidLeft.set(!climberSolenoidLeft.get());
    climberSolenoidRight.set(!climberSolenoidRight.get());
  }

  public void setClimber(boolean active) {
    climberSolenoidLeft.set(active);
    climberSolenoidRight.set(active);
  }
}
