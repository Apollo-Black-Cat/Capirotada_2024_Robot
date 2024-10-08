// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
 
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class SetOnClimber extends Command {
  /** Creates a new ClimberToogle. */
  ClimberSubsystem climberSubsystem;
  LEDSubsystem ledSubsystem;

  public SetOnClimber(ClimberSubsystem climberSubsystem, LEDSubsystem ledSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climberSubsystem = climberSubsystem;
    addRequirements(climberSubsystem);
    this.ledSubsystem = ledSubsystem;
    addRequirements(ledSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Sistema de trepadado de cadena iniciado!");
    this.ledSubsystem.ledInit(5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.climberSubsystem.setClimber(true);
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.ledSubsystem.ledInit(1);
    System.out.println("Sistema de treapado de cadena finalizado!");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
