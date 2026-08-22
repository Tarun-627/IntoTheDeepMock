package org.firstinspires.ftc.teamcode.Autonomous.RedAuto;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.pedro.PedroCommands.*;
import static org.firstinspires.ftc.teamcode.PedroPathing.Tuning.follower;

import static com.pedropathing.ivy.groups.Groups.sequential;

import org.firstinspires.ftc.teamcode.Subsystems.Extendo_Slides;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.ArmClaw;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.ArmClawPivot;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakePivot;
import org.firstinspires.ftc.teamcode.Subsystems.Lift_Slides;


@Autonomous
public class RedHighBoxAuto extends OpMode {

    // ArmClaw Define
    private ArmClaw armclaw;

    // ArmClawPivot Define
    private ArmClawPivot armclawpivot;

    // Intake Define
    private Intake intake;

    // IntakePivot Define
    private IntakePivot intakepivot;

    // Extendo Slides Define
    private Extendo_Slides slides;

    // Lift Slides Define
    private Lift_Slides liftslides;

    private final Pose startPos = new Pose(103.32441, 33.3974358, Math.toRadians(90)); // random positions based upon pedro pathing visualizer for decode
    private final Pose grabPos = new Pose(70.35562, 49.848383, Math.toRadians(90)); // random positions based upon pedro pathing visualizer for decode
    private final Pose scorePos = new Pose(60, 84, Math.toRadians(135)); // random positions based upon pedro pathing visualizer for decode

    private CommandBuilder autoRoutine;



    @Override
    public void init() {

        PathChain grabArtifact, scoreArtifact;


        grabArtifact = follower.pathBuilder()
                .addPath(new BezierLine(startPos, grabPos))
                .setLinearHeadingInterpolation(startPos.getHeading(), grabPos.getHeading())
                .build();

        scoreArtifact = follower.pathBuilder()
                .addPath(new BezierLine(grabPos, scorePos))
                .setLinearHeadingInterpolation(grabPos.getHeading(), scorePos.getHeading())
                .build();


        final Pose startPos = new Pose(37.858974358974365, 33.3974358, Math.toRadians(90)); // random positions based upon pedro pathing visualizer for decode
        final Pose grabPos = new Pose(59.344, 59.344, Math.toRadians(90)); // random positions based upon pedro pathing visualizer for decode
        final Pose scorePos = new Pose(21.417, 19.750, Math.toRadians(45)); // random positions based upon pedro pathing visualizer for decode


        // ArmClaw Init
        armclaw = new ArmClaw(hardwareMap);

        // ArmClawPivot Init
        armclawpivot = new ArmClawPivot(hardwareMap);

        // Intake Init
        intake = new Intake(hardwareMap);

        // Intake Pivot Init
        intakepivot = new IntakePivot(hardwareMap);

        // Extendo Slides Init
        slides = new Extendo_Slides(hardwareMap);

        // Lift Slides Init
        liftslides = new Lift_Slides(hardwareMap);

        autoRoutine = sequential(
                parallel (
                        follow(follower, grabArtifact),
                        intake.intake(),
                        slides.extend()
                ),
                waitMs(1000),
                slides.transfer(),
                intakepivot.intakepivoted(),
                armclaw.armclawgrab(),
                parallel(
                        follow(follower, scoreArtifact),
                        liftslides.highbox()

                ),
                armclawpivot.armclawpivoted(),
                armclaw.armclawrelease()

        );

    }

    public void start() {
        autoRoutine.schedule();
    }

    public void loop() {
        Scheduler.execute();
    }



}
