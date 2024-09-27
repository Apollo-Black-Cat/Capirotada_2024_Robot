// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class IntakeCommand extends Command {
  /** Creates a new IntakeCommand. */
  LEDSubsystem ledSubsystem;
  IntakeSubsystem intakeSubsystem;
  ConveyorSubsystem conveyorSubsystem;
  int ledMode;
  boolean isOpen;

  public IntakeCommand(IntakeSubsystem intakeSubsystem, ConveyorSubsystem conveyorSubsystem, LEDSubsystem ledSubsystem, boolean isOpen, int ledMode) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intakeSubsystem = intakeSubsystem;
    this.conveyorSubsystem = conveyorSubsystem;
    this.ledSubsystem = ledSubsystem;
    this.isOpen = isOpen;
    this.ledMode = ledMode;
    addRequirements(intakeSubsystem);
    addRequirements(conveyorSubsystem);
    addRequirements(ledSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("Intake Active", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.intakeSubsystem.setPosition(isOpen);
    this.conveyorSubsystem.setPosition(isOpen);
    this.ledSubsystem.ledInit(ledMode);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.conveyorSubsystem.stopMotors();
    this.intakeSubsystem.stopIntake();
    this.ledSubsystem.ledInit(1);
    SmartDashboard.putBoolean("Inatke Active", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
