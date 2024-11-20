package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterConfig {

    //CAN IDs
    public static int right_ID = 20;
    public static int left_ID = 21;

    //motor configs
    public static MotorType motorType = MotorType.kBrushless;
    public static IdleMode idleMode = IdleMode.kCoast;

    //PID values
    public static double p = 0.01;
    public static double i = 0;
    public static double d = 0;

    //output
    public static double outputMin = -1;
    public static double outputMax = 1;

    //ramp rate
    public static double openRampRate = 0.5;
    public static double closeRampRate = 0.5;

    //inversion
    public static boolean inverted = false;

    //current limits
    public static int currentStallLimit = 40;
    public static int currentFreeLimit = 30;

    //conversion factors
    public static double velocityConversionFactor = 1;
    

    
}
