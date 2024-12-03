// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  
  // Create motor, encoder, and PID instances
  private CANSparkMax armMotor;
  private RelativeEncoder armEncoder;
  private SparkPIDController armPID;

  //This is for Shuffleboard.
  GenericEntry stabilizerPosition;

  public Arm() 
  {
    // Initialize motor
    armMotor = new CANSparkMax(ArmConfig.ID, ArmConfig.motorType);

    // Initialize encoder instance
    armEncoder = armMotor.getEncoder();

    // Initialize PID instance
    armPID = armMotor.getPIDController();

    // PID Configuration
    armPID.setP(ArmConfig.p);
    armPID.setI(ArmConfig.i);
    armPID.setD(ArmConfig.d);
    armPID.setIZone(ArmConfig.IZone);
    armPID.setDFilter(ArmConfig.DFilter);

    // Set output range
    armPID.setOutputRange(ArmConfig.outputMin, ArmConfig.outputMax);

    // Set ramp rate
    armMotor.setOpenLoopRampRate(ArmConfig.openRampRate);
    armMotor.setClosedLoopRampRate(ArmConfig.closedRampRate);

    // Set inversion
    armMotor.setInverted(ArmConfig.kInverted);

    // Set current limits
    armMotor.setSmartCurrentLimit(ArmConfig.smartCurrentStallLimit, ArmConfig.smartCurrentFreeLimit);

    // Set position conversion factor
    armEncoder.setPositionConversionFactor(ArmConfig.positionConversionFactor);

    // Set forward soft limit
    armMotor.enableSoftLimit(SoftLimitDirection.kForward, ArmConfig.softLimitFwdEnabled);
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConfig.softLimitFwd);

    // Set reverse soft limit
    armMotor.enableSoftLimit(SoftLimitDirection.kReverse, ArmConfig.softLimitRevEnabled);
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConfig.softLimitRev);

    // Burn Flash
    armMotor.burnFlash();

    set(ArmPositions.zero);

    shuffleBoardInit();
  }

  @Override
  public void periodic() {
    updateShuffleboard();
  }

  // Get position
  public double get() {
    return armEncoder.getPosition();
  }

  // Set position
  public void set(double position) {
    armPID.setReference(position, ArmConfig.controlType);
  }

  public void resetEncoder()
  {
    armEncoder.setPosition(0);
  }

  public void shuffleBoardInit()
  {
    stabilizerPosition = Shuffleboard.getTab("Info")
      .add("Arm Position", armEncoder.getPosition())
      .getEntry();
  }

  public void updateShuffleboard()
  {
    stabilizerPosition.setDouble(armEncoder.getPosition());
  }

}
