package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.*;

public class FullBase extends RobotBase {
    public Drivetrain drivetrain;
    public Neck neck;

    private RobotComponent[] components = new RobotComponent[2];

    public double rpm = 0;

    public FullBase(Telemetry telemetry, LinearOpMode opMode, HardwareMap hardwaremap, boolean debugging) {
        super(telemetry, opMode, hardwaremap,debugging);
    }

    @Override
    public void init() {
        //create drivetrain
        telemetry.addLine("Drivetrain about to init");
        drivetrain = new Drivetrain(this);
        telemetry.addLine("drive inited");
        components[0] = drivetrain;
    }

    /**
     * @param timeInMs
     * */
    public void wait(double timeInMs){

        while(this.opMode.time < (timeInMs/1000));
    }
    @Override
    public void stop() {
        for( int i = 0; i<=1; i++){
            components[i].stop();
        }
    }
}
