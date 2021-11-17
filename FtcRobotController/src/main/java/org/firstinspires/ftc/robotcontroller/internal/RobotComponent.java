package org.firstinspires.ftc.robotcontroller.internal;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;

public abstract class RobotComponent {
   protected RobotBase base;

    public RobotComponent(RobotBase base){
        this.base = base;
    }

    public final RobotBase base()
    {

        return base;
    }

    public abstract void stop();
}
