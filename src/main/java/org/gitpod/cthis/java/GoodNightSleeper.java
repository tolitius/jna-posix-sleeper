package org.gitpod.cthis.java;

import com.sun.jna.Native;
import org.gitpod.cthis.java.posix.Posix;
import org.gitpod.cthis.java.posix.TimeSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> POSIX usleep has a microsecond precision: "The  usleep()  function	shall cause the calling thread to be
 *     suspended from execution until either the number of realtime microseconds" </p>
 *
 * <p> Hence picking at POSIX's "pselect", but not for its ability to "monitor multiple file descriptors", rather for
 *     its ability to handle timeouts with a nanosecond precision.</p>
 *
 * <p> Who are we kidding!? Of course this is not run against hard realtime RTOS microkernel, and of course JNA it self
 *     has a greater than several microseconds overhead, but hey, it's fun !!! </p>
 *
 * @author anatoly.polinsky
 */
public final class GoodNightSleeper {

    private final static Logger logger = LoggerFactory.getLogger( GoodNightSleeper.class );
    private final static int NANOSECONDS_TO_SECOND = 1000000000;

    // loading exposed POSIX functions ( e.g. "pselect" )
    private Posix posix = ( Posix ) Native.loadLibrary( "c", Posix.class );

    /**
     * <p> Conducts a sleep study given a number of nanoseconds </p>
     *
     * @param nanoseconds to sleep
     * @return actual time slept in nanoseconds
     */
    public long sleepStudy( long nanoseconds ) {

        // not to waste precious nanoseconds on creating this inline
        TimeSpec timeSpec = timeSpecFromNanoSeconds( nanoseconds );

        long start = System.nanoTime();
        this.posix.pselect( 0, null, null, null, timeSpec, null );
        long end = System.nanoTime();

        return ( end - start );
    }

    /**
     * <p> Creates ala C "timespec" struct, and initializes it to a given number of nanoseconds </p>
     *
     * @param nanoseconds to init a "timespec" struct with
     * @return initialized "timespec" C struct
     */
    private TimeSpec timeSpecFromNanoSeconds( long nanoseconds ) {
        return new TimeSpec( nanoseconds / NANOSECONDS_TO_SECOND,
                             nanoseconds % NANOSECONDS_TO_SECOND );
    }

    /**
     * <p> Point of entry: "You Are Here" </p>
     */
    public static void main( String[] args ) {

        GoodNightSleeper goodNightSleeper = new GoodNightSleeper();

        logger.info( "sleeping for 1 nanosecond\t\t:"  + goodNightSleeper.sleepStudy( 1 ) + " nanoseconds" );
        logger.info( "sleeping for 1 microsecond\t\t:" + goodNightSleeper.sleepStudy( 1000 ) + " nanoseconds" );
        logger.info( "sleeping for 1 millisecond\t\t:" + goodNightSleeper.sleepStudy( 1000000 ) + " nanoseconds" );
        logger.info( "sleeping for 1 second\t\t\t:"    + goodNightSleeper.sleepStudy( 1000000000 ) + " nanoseconds" );
    }
}
