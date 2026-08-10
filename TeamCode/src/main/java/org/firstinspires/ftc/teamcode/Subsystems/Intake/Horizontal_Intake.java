package org.firstinspires.ftc.teamcode.Subsystems.Intake;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Horizontal_Intake {
    public enum State {GRAB, OPEN};
    private final Servo HorizontalClaw;

    State state = State.OPEN;
    public Horizontal_Intake(HardwareMap hardwareMap){
        HorizontalClaw = hardwareMap.get(Servo.class, "HorizontalClaw");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case GRAB:
                HorizontalClaw.setPosition(0.7); // Assumed grip position for claw servo
                break;
            case OPEN:
                HorizontalClaw.setPosition(0.2); // // Assumed open position for claw servo

        }
    }

    public Command hgrab(){
        return instant(() -> setState(State.GRAB)).requiring(this);
    }
    public Command hrelease(){
        return instant(() -> setState(State.OPEN)).requiring(this);
    }
}
