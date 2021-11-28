package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class DuckeySpinner extends RobotComponent {
    public Servo spinner;
    public static final double moveCount = 0; // TODO: fill with accurate number

    public DuckeySpinner(RobotBase base){
        super(base);
        spinner = base.getMapper().mapServo("duckeySpinner");
    }

    public void spin(boolean button){
        if(button){
            spinner.setPosition((spinner.getPosition() + moveCount) % 1);
        }
    }

    @Override
    public void stop() {}
}
