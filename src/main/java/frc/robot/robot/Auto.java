package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.arm.ArmPositionCommand;
import frc.robot.commands.shooter.ShooterSpeedCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
    private final Swerve swerve;
    private final Arm arm;
    private final Shooter shooter;

    // Autonomous commands
    private Command auto1;
    private Command auto2;
    private Command auto3;
    // Add more as needed

    // SendableChooser for selecting autonomous command
    private final SendableChooser<Command> autoChooser;

    public Auto(RobotContainer robot) {
        swerve = robot.getSwerve();
        arm = robot.getArm();
        shooter = robot.getShooter();

        // Initialize autonomous commands
        setupAutonomousCommands();

        // Initialize chooser
        autoChooser = new SendableChooser<>();
        autoChooser.setDefaultOption("Auto 1", auto1);
        autoChooser.addOption("Auto 2", auto2);
        autoChooser.addOption("Auto 3", auto3);
        // Add more options as needed

        // Put chooser on SmartDashboard
        SmartDashboard.putData("Auto Mode", autoChooser);
    }

    private void setupAutonomousCommands() {
        
        // Register Named Commands
        NamedCommands.registerCommand("ShooterHighSpeed", new ShooterSpeedCommand(shooter, 1, false));
        NamedCommands.registerCommand("StopShooter", new ShooterSpeedCommand(shooter, 0, true));
        NamedCommands.registerCommand("ArmToZero", new ArmPositionCommand(arm, 0));

        // Setup autonomous commands using swerve subsystem
        auto1 = swerve.getAutoCommand("Auto1");
        auto2 = swerve.getAutoCommand("Auto2");
        auto3 = swerve.getAutoCommand("Auto3");
        // Initialize other autonomous commands as needed
    }

    public Command getAutonomousCommand() {
        // Return the selected autonomous command
        return autoChooser.getSelected();
    }

}
