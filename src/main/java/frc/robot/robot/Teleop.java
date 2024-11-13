package frc.robot.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.drivebase.Swerve;

public class Teleop {

    //subsystems
    private final Swerve swerve;

    //joysticks
    private final CommandJoystick driveStick;
    private final CommandJoystick rotationStick;

    //create triggers
    private final Trigger zeroGyro;
    private final Command zeroGyroCommand;

    //create commands
    private final DriveCommand driveCommand;


    public Teleop(RobotContainer robot){

        swerve = robot.getSwerve();

        //init joysticks
        driveStick = new CommandJoystick(0);
        rotationStick = new CommandJoystick(1);

        //init trigger
        zeroGyro = driveStick.button(16);

        //init command
        driveCommand = new DriveCommand(swerve, driveStick, rotationStick);
        zeroGyroCommand = swerve.runOnce(() -> swerve.seedFieldRelative());

        swerve.setDefaultCommand(driveCommand);


    }

    public void configureBindings(){
        zeroGyro.onTrue(zeroGyroCommand);
    }
}
