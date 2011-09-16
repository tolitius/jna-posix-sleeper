package org.gitpod.cthis.scala

import org.slf4j.LoggerFactory
import java.lang.Long
import com.sun.jna.{Native, Structure, Library}
import posix.{TimeSpec, Posix}

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
object GoodNightSleeper {

    val logger = LoggerFactory.getLogger( GoodNightSleeper.getClass )
    val NANOSECONDS_TO_SECOND = 1000000000

    // loading exposed POSIX functions ( e.g. "pselect" )
    val posix = Native.loadLibrary( "c", classOf[Posix] ).asInstanceOf[Posix]

    /**
     * <p> Conducts a sleep study given a number of nanoseconds </p>
     *
     * @param posix native library to hook into
     * @param nanoseconds to sleep
     * @return actual time slept in nanoseconds
     */
    def sleepStudy( nanoseconds: Long ) = {

        // not to waste precious nanoseconds on creating this inline
        val timeSpec = timeSpecFromNanoSeconds( nanoseconds )

        val start = System.nanoTime()
        posix.pselect( 0, null, null, null, timeSpec, null )
        val end = System.nanoTime()

        end - start
    }

    /**
     * <p> Creates ala C "timespec" struct, and initializes it to a given number of nanoseconds </p>
     *
     * @param nanoseconds to init a "timespec" struct with
     * @return initialized "timespec" C struct
     */
    def timeSpecFromNanoSeconds( nanoseconds: Long ) = {
        new TimeSpec( nanoseconds / NANOSECONDS_TO_SECOND,
                      nanoseconds % NANOSECONDS_TO_SECOND )
    }

    /**
     * <p> Point of entry => "You Are Here" </p>
     */
    def main( args: Array[String] ) {

        logger.info( "sleeping for 1 nanosecond\t\t:"  + sleepStudy( 1 ) + " nanoseconds" )
        logger.info( "sleeping for 1 microsecond\t:"   + sleepStudy( 1000 ) + " nanoseconds" )
        logger.info( "sleeping for 1 millisecond\t:"   + sleepStudy( 1000000 ) + " nanoseconds" )
        logger.info( "sleeping for 1 second\t\t\t:"    + sleepStudy( 1000000000 ) + " nanoseconds" )
    }
}
