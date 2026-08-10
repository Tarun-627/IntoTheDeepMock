package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HorizontalClawRotate {
    public enum State {NORMAL, TRANSFER};
    private final Servo HorizontalClawRotate;

    State state = State.NORMAL;
    public HorizontalClawRotate(HardwareMap hardwareMap){
        HorizontalClawRotate = hardwareMap.get(Servo.class, "Horizontal Claw Rotate");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case NORMAL:
                HorizontalClawRotate.setPosition(-1.0); // Assumed for position for claw servo rotator
                break;
            case TRANSFER:
                HorizontalClawRotate.setPosition(1.0); // Assumed for position for claw servo rotator

        }
    }

    public Command hnormal(){
        return instant(() -> setState(State.NORMAL)).requiring(this);
    }
    public Command htransfer(){
        return instant(() -> setState(State.TRANSFER)).requiring(this);
    }
}
