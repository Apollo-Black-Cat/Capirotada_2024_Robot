// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticsSubsystem extends SubsystemBase {
  private final Compressor m_Compressor;

  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {
    m_Compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Presion Completa", m_Compressor.getPressureSwitchValue());
    SmartDashboard.putNumber("presion", m_Compressor.getCurrent());
  }


  public void setCompressor(boolean IsActive) {
    if (IsActive)
      m_Compressor.enableDigital();
    else
      m_Compressor.disable();
  }
}
