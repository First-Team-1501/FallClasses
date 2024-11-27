// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

  private CANSparkMax armMotor;
  private RelativeEncoder armEncoder;
  private SparkPIDController armPID;

  // Shuffleboard
  private GenericEntry armPosition; 

  public Arm() 
  {

    //Initalize motor
    armMotor = new CANSparkMax(ArmConfig.ID, ArmConfig.motorType);
    armEncoder = armMotor.getEncoder();
    armPID = armMotor.getPIDController();

    //PID config
    armPID.setP(ArmConfig.p);
    armPID.setI(ArmConfig.i);
    armPID.setD(ArmConfig.d);

    // Set output range
    armPID.setOutputRange(ArmConfig.outputmin, ArmConfig.outputmax);

    // Set ramp rate
    armMotor.setOpenLoopRampRate(ArmConfig.openRampRate);
    armMotor.setClosedLoopRampRate(ArmConfig.closedRampRate);

    // Set inversion
    armMotor.setInverted(ArmConfig.invereted);

    //Set current limits
    armMotor.setSmartCurrentLimit(ArmConfig.smartCurrentStallLimit, ArmConfig.smartCurrentfreeLimit);

    // Set position conversion factor
    armEncoder.setPositionConversionFactor(ArmConfig.positionConversionFactor);

    // Set forward soft limit
    armMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConfig.softLimitFwd);

    // Set revereced soft limit
    armMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConfig.softLimitRev);

    armMotor.burnFlash();

    set(0);

    shuffleboardInit();

  }

  // Get position
  public double get()
  {
    return armEncoder.getPosition();
  }

  // Set position function
  public void set(double position)
  {
    armPID.setReference(position, ArmConfig.controlType);
  }

  public void shuffleboardInit()
  {
    armPosition = Shuffleboard.getTab("Robot Stats")
    .add("Arm Position", get())
    .getEntry();
  }

  public void shuffleboardUpdate()
  {
    armPosition.setDouble(get());
  }

  @Override
  public void periodic() 
  {
    shuffleboardUpdate();
  }
}
