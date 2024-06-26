// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
  /** Creates a new IntakeCommand. */
  IntakeSubsystem intakeSubsystem;
  ConveyorSubsystem conveyorSubsystem;
  boolean isOpen;

  public IntakeCommand(IntakeSubsystem intakeSubsystem, ConveyorSubsystem conveyorSubsystem, boolean isOpen) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intakeSubsystem = intakeSubsystem;
    this.conveyorSubsystem = conveyorSubsystem;
    this.isOpen = isOpen;
    addRequirements(intakeSubsystem);
    addRequirements(conveyorSubsystem);
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
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.conveyorSubsystem.stopMotors();
    this.intakeSubsystem.stopIntake();
    SmartDashboard.putBoolean("Inatke Active", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
