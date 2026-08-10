package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class VerticalClawRotate {
    public enum State {VNORMAL, VTRANSFER};
    private final Servo VerticalClawRotate;

    State state = State.VNORMAL;
    public VerticalClawRotate(HardwareMap hardwareMap){
        VerticalClawRotate = hardwareMap.get(Servo.class, "Vertical Claw Rotate");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case VNORMAL:
                VerticalClawRotate.setPosition(1.0); // Assumed for position for claw servo rotator
                break;
            case VTRANSFER:
                VerticalClawRotate.setPosition(-1.0); // Assumed for position for claw servo rotator

        }
    }

    public Command vnormal(){
        return instant(() -> setState(State.VNORMAL)).requiring(this);
    }
    public Command vtransfer(){
        return instant(() -> setState(State.VTRANSFER)).requiring(this);
    }
}
