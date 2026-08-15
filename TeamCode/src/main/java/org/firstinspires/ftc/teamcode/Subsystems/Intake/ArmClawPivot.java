package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmClawPivot {
    public enum State {NORMAL, PIVOT};
    private final Servo armclawpivot;

    State state = State.NORMAL;
    public ArmClawPivot(HardwareMap hardwareMap){
        armclawpivot = hardwareMap.get(Servo.class, "Arm Claw Pivot");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case NORMAL:
                armclawpivot.setPosition(1.0); // Assumed for position for claw servo rotator
                break;
            case PIVOT:
                armclawpivot.setPosition(-1.0); // Assumed for position for claw servo rotator
                break;

        }
    }

    public Command armclawnormal(){
        return instant(() -> setState(State.NORMAL)).requiring(this);
    }
    public Command armclawpivoted(){
        return instant(() -> setState(State.PIVOT)).requiring(this);
    }
}
