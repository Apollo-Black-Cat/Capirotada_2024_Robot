// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterActivateCommand extends Command {
  /** Creates a new SlowShooter. */
  private final ShooterSubsystem shooterSubsystem;
  private final ConveyorSubsystem conveyorSubsystem;
  private final boolean isShooting;

  public ShooterActivateCommand(ShooterSubsystem shooterSubsystem, ConveyorSubsystem conveyorSubsystem,
      boolean isShooting) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooterSubsystem = shooterSubsystem;
    addRequirements(shooterSubsystem);
    this.conveyorSubsystem = conveyorSubsystem;
    addRequirements(conveyorSubsystem);
    this.isShooting = isShooting;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Shooter iniciado");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (isShooting)
    //Este comando paralelo hace que el shooter active, y el conveyor espere 1 segundo antes de activarse.
      new ParallelCommandGroup(new ShooterCommand(shooterSubsystem, true),
          new SequentialCommandGroup(new WaitCommand(0.8), new ConveyorCommand(conveyorSubsystem, true)));
    else {
      shooterSubsystem.ActivateShooter(isShooting);
      conveyorSubsystem.setPosition(isShooting);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.stopMotors();
    conveyorSubsystem.stopMotors();  
    System.out.println("Shooter apagado");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
