package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArmConfig {
    
    // **** ARM CONFIG ****

    // CAN bus ID
    public static int ID = 22;

    // Type configs
    public static MotorType motorType = MotorType.kBrushless;

    // PIDF Values
    public static double p = 0.1; // .01 good start value
    public static double i = 0;
    public static double d = 0;
    public static double f = 0;
    public static double IZone = 0;
    public static double DFilter = 0;

    // Idle Mode
    public static IdleMode idleMode = IdleMode.kBrake; 

    // Min + Max Output
    public static double outputMin = -1;
    public static double outputMax = 1;

    // Ramp Rate
    public static double openRampRate = 0; // Time in seconds (1 is good for percent based stuff) (percent mode)
    public static double closedRampRate = 0; // IF IN POSITION MODE, THIS IS THE RAMP RATE SETTING!!!


    // Inverted
    public static boolean kInverted = true;

    // Current Limits
    public static int smartCurrentStallLimit = 40; // 40 for big neo, 20 for small neo
    public static int smartCurrentFreeLimit = 30; // 30 for big neo, 15 for small neo

    // Conversion Factors
    public static double positionConversionFactor = 1;
    public static double velocityConversionFactor = 1;

    // Soft Limits Enabled
    public static boolean softLimitFwdEnabled = true;
    public static boolean softLimitRevEnabled = true;

    // Soft Limits
    public static float softLimitFwd = 20;
    public static float softLimitRev = -20;

    // Analog for Hollow Bore
    public static double analogPositionConversion = 1;
    public static double analogVelocityConversion = 1;
    public static int analogSensorMore = 0;
    public static int analogInverted = 0;

    // Control Type
    public static ControlType controlType = ControlType.kPosition;

}