// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.Shooter;

public class ShooterSpeedCommand extends Command {
  /** Creates a new ShooterSpeedCommand. */

  private Shooter shooter;
  private double speed;

  public ShooterSpeedCommand(Shooter shooter, double speed) {
    
    this.shooter = shooter;
    this.speed = speed;

    addRequirements(shooter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    shooter.set(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
