// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {
  private final ShooterSubsystem shooterSubsystem;
  private final LEDSubsystem ledSubsystem;
  private boolean isShooting;

  /** Creates a new ShooterCommand. */
  public ShooterCommand(ShooterSubsystem shooterSubsystem, LEDSubsystem ledSubsystem, boolean isShooting) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ledSubsystem = ledSubsystem;
    addRequirements(ledSubsystem);
    this.shooterSubsystem = shooterSubsystem;
    addRequirements(shooterSubsystem);
    this.isShooting = isShooting;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Motores del shooter iniciados!");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.shooterSubsystem.ActivateShooter(isShooting);
    if (isShooting) {
      this.ledSubsystem.ledInit(2);
    }
    else {
      this.ledSubsystem.ledInit(6);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.shooterSubsystem.stopMotors();
    this.ledSubsystem.ledInit(1);
    System.out.println("Motores del shooter apagados!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
