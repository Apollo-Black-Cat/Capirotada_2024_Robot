// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.commands.SetOnClimber;
import frc.robot.commands.SetPneumatics;
import frc.robot.commands.ConveyorCommand;
import frc.robot.commands.ConveyorSetOff;
import frc.robot.commands.ConveyorSetOn;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.SetOffClimber;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.ShooterSetOff;
import frc.robot.commands.ShooterSetOn;
import frc.robot.commands.ShooterActivateCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrainSubsystem m_robotDrive;
  private final IntakeSubsystem m_intakeSubsystem;
  private final ConveyorSubsystem m_conveyorSubsystem;
  private final ShooterSubsystem m_shooterSubsystem;
  private final ClimberSubsystem m_climberSubsystem;
  private final PneumaticsSubsystem m_pneumaticsSubsystem;
  private final LEDSubsystem m_ledSubsystem;

  // Mando del driver
  private final CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort);

  // Objeto que guarda los autonomos disponibles
  private final SendableChooser<String> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_robotDrive = new DriveTrainSubsystem();
    m_intakeSubsystem = new IntakeSubsystem();
    m_conveyorSubsystem = new ConveyorSubsystem();
    m_climberSubsystem = new ClimberSubsystem();
    m_pneumaticsSubsystem = new PneumaticsSubsystem();
    m_shooterSubsystem = new ShooterSubsystem();
    m_ledSubsystem = new LEDSubsystem();

    // Creacion de las opciones de autonomos
    autoChooser = new SendableChooser<>();
    autoChooser.setDefaultOption("Default Option", "Default Option");
    autoChooser.addOption("Izquierda Simple", "Iz. Simple");
    autoChooser.addOption("Centro Simple", "C. Simple");
    autoChooser.addOption("Derecha Simple", "D. Simple");
    autoChooser.addOption("Izquierda", "Izquierda");
    autoChooser.addOption("Centro", "Centro");
    autoChooser.addOption("Derecha", "Derecha");

    NamedCommands.registerCommand("useIntake", new IntakeCommand(m_intakeSubsystem, m_conveyorSubsystem, m_ledSubsystem, true, 3));
    NamedCommands.registerCommand("Encender Shooter", new ShooterSetOn(m_shooterSubsystem));
    NamedCommands.registerCommand("Apagar Shooter", new ShooterSetOff(m_shooterSubsystem));
    NamedCommands.registerCommand("EncenderConveyor", new ConveyorSetOn(m_conveyorSubsystem));
    NamedCommands.registerCommand("ApagarConveyor", new ConveyorSetOff(m_conveyorSubsystem));
    NamedCommands.registerCommand("Encender Compressor", new SetPneumatics(m_pneumaticsSubsystem, true));
    NamedCommands.registerCommand("Apagar Compressor", new SetPneumatics(m_pneumaticsSubsystem, false));

    // Crear la seleccion de autonomos
    SmartDashboard.putData("Seleccionador de autonomos", autoChooser);

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    m_robotDrive.setDefaultCommand(new RunCommand(
        () -> m_robotDrive.arcadeDrive(m_driverController.getLeftY(),
            -m_driverController.getRightX() * 1.5),
        m_robotDrive));

    m_driverController.b().whileTrue(new IntakeCommand(m_intakeSubsystem, m_conveyorSubsystem, m_ledSubsystem, false, 4));
    m_driverController.a().whileTrue(new IntakeCommand(m_intakeSubsystem, m_conveyorSubsystem, m_ledSubsystem, true, 3));
    m_driverController.rightBumper().whileTrue(new ParallelCommandGroup(new ShooterCommand(m_shooterSubsystem, m_ledSubsystem, false),
        new ConveyorCommand(m_conveyorSubsystem, false)));
    m_driverController.leftBumper().whileTrue(new ParallelCommandGroup(new ShooterCommand(m_shooterSubsystem, m_ledSubsystem, true),
        new SequentialCommandGroup(new WaitCommand(0.75), new ConveyorCommand(m_conveyorSubsystem, true))));
    m_driverController.povUp().onTrue(new SetOnClimber(m_climberSubsystem, m_ledSubsystem));
    m_driverController.povDown().onTrue(new SetOffClimber(m_climberSubsystem));
    m_driverController.povLeft().whileTrue(new ShooterActivateCommand(m_shooterSubsystem, m_conveyorSubsystem, true));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Ejecutar el autonomo seleccionado
    Command selectedCommand;
    switch (autoChooser.getSelected()) {
      case "Default Option":
        selectedCommand = new Command() {
        };
      case "Iz. Simple":
        selectedCommand = AutoBuilder.buildAuto("Izquierda Simple");
        break;
      case "D. Simple":
        selectedCommand = AutoBuilder.buildAuto("Derecha Simple");
        break;
      case "C. Simple":
        selectedCommand = AutoBuilder.buildAuto("Centro Simple");
        break;
      case "Izquierda":
        selectedCommand = AutoBuilder.buildAuto("Izquierda");
        break;
      case "Derecha":
        selectedCommand = AutoBuilder.buildAuto("Derecha");
        break;
      case "Centro":
        selectedCommand = AutoBuilder.buildAuto("Centro");
        break;
      default:
        selectedCommand = new Command() {
        };
        break;
    }
    return selectedCommand;
  }
}
