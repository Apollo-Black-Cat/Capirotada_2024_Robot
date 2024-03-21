// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;

public class ConveyorCommand extends Command {
  private final ConveyorSubsystem conveyorSubsystem;
  private boolean isShooting;
  /** Creates a new ConveyorCommand. */
  public ConveyorCommand(ConveyorSubsystem conveyorSubsystem, boolean isShooting) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.conveyorSubsystem = conveyorSubsystem;
    addRequirements(conveyorSubsystem);
    this.isShooting = isShooting;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Conveyor iniciado!");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.conveyorSubsystem.setPosition(isShooting);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.conveyorSubsystem.stopMotors();
    System.out.println("Sistema de conveyor terminado");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
