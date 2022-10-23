package com.example.orderorchestrator.saga.service;


import java.util.List;

public class OpenAccountWorkflow implements Workflow  {

    private final List<WorkflowStep> steps;

    public OpenAccountWorkflow(List<WorkflowStep> steps) {
        this.steps = steps;
    }
    @Override
    public List<WorkflowStep> getSteps() {
        return this.steps;
    }
}
