package frc.robot.robot;

import com.fasterxml.jackson.databind.util.Named;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Commands.arm.ArmPositionCommand;
import frc.robot.Commands.shooter.ShooterSpeedCommand;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.drivebase.Swerve;
import frc.robot.subsystems.shooter.Shooter;

public class Auto {
    
    private final Swerve swerve;
    private final Arm arm;
    private final Shooter shooter;

    //Auto commands
    private Command auto1;
    private Command auto2;
    private Command auto3;

    private final SendableChooser<Command> autoChooser;
   
    public Auto(RobotContainer robot)
    {
        swerve = robot.getSwerve();
        arm = robot.getArm();
        shooter = robot.getShooter();

        setupAuto();

        autoChooser = new SendableChooser<>();
        autoChooser.setDefaultOption("Auto 1", auto1);
        autoChooser.addOption("Auto 2", auto2);
        autoChooser.addOption("Auto 3", auto3);

        SmartDashboard.putData("Auto Mode", autoChooser);

    }

    private void setupAuto()
    {
        //Regester Named Commands
        NamedCommands.registerCommand("ShooterHighSpeed", new ShooterSpeedCommand(shooter, 1));
        NamedCommands.registerCommand("StopShooter", new ShooterSpeedCommand(shooter, 1));
        NamedCommands.registerCommand("ArmToZero", new ArmPositionCommand(arm, 0));

        //Setup auto commands
        auto1 = swerve.getAutoCommand("Auto1");
        auto2 = swerve.getAutoCommand("Auto2");
        auto3 = swerve.getAutoCommand("Auto3");
    }

    public Command getAutoCommand()
    {
        return autoChooser.getSelected();
    }

}
