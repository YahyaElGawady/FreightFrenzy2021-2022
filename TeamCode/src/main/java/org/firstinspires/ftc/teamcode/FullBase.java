package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.*;

public class FullBase extends RobotBase {
    public Drivetrain drivetrain;
    public Sucker sucker;
    public OuttakeBucket outtakeBucket;
    private RobotComponent[] components = new RobotComponent[3];

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

        telemetry.addLine("Sucker about to init");
        sucker = new Sucker(this);
        telemetry.addLine("sucker inited");
        components[1] = sucker;

        telemetry.addLine("Outtake about to init");
        outtakeBucket = new OuttakeBucket(this);
        telemetry.addLine("outtake inited");
        components[2] = outtakeBucket;
    }

    /**
     * @param timeInMs
     * */
    public void wait(double timeInMs){

        while(this.opMode.time < (timeInMs/1000));
    }
    @Override
    public void stop() {
        for( int i = 0; i<components.length; ++i){
            components[i].stop();
        }
    }
}
