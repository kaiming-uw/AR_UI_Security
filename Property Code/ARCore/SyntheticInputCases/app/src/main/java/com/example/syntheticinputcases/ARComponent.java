package com.example.syntheticinputcases;

/**
 * Interface that defines copmonent behavior for an experiment
 */
public interface ARComponent {
    /**
     * Method to launch the component. Should only be called
     * once on startup of the harness.
     */
    void launch();

    /**
     * Initiates the action for the component necessary to
     * carry forward the next experiment as defined by
     * the given experiment number
     *
     * @param experimentNum experiment number ID
     */
    void next(int experimentNum);
}
