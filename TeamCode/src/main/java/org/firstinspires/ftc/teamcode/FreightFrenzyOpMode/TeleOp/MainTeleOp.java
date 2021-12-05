package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Main TeleOp", group = "Linear Opmode")
public class MainTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    FullBase Base ;


    boolean dpadUpHeld = false;
    boolean dpadDownHeld = false;
    boolean dpadRightHeld = false;
    boolean dpadLeftHeld  = false;
    boolean gamepad1XHeld = false;
    boolean gamepad1YHeld = false;

    boolean flickerPositon =true;
    boolean firstTime = true;
    boolean slowMode = false;
    double initTime;
    int initPos;

    @Override
    public void runOpMode() {
        Base = new FullBase(telemetry,this, hardwareMap, true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Base.init();
        telemetry.addLine("done with init");
        waitForStart();
        while (opModeIsActive()){
            /*      drivetrain      */
            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            Base.drivetrain.drive(forward, turn, slowMode);
            /*      intake      */
            Base.sucker.moveArmInTeleop(gamepad1.right_bumper);
            Base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);
            if(gamepad1.right_trigger < .1)
                Base.sucker.moveSuckerInTeleop(-gamepad1.left_trigger);
            /*      outtake     */
            Base.outtakeBucket.dump(gamepad2.right_trigger > 0.5);
            /*   ducky spinner  */
           // Base..spin(gamepad2.y);

        }
    }
}
