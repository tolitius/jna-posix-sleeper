package org.gitpod.cthis.java.posix;

import com.sun.jna.Library;

/**
 * <p> JNAing POSIX's pselect => making it loadable by JNA "Native" </p>
 *
 * @author anatoly.polinsky
 */
public interface Posix extends Library {

    /**
    * http://linux.die.net/man/2/pselect
    *
    * int pselect( int nfds,
    *              fd_set *readfds,
    *              fd_set *writefds,
    *              fd_set *exceptfds,
    *              const struct timespec *timeout,
    *              const sigset_t *sigmask );
    */
    int pselect ( int nfds,
                  Object readfds,
                  Object writefds,
                  Object exceptfds,
                  TimeSpec timeout,
                  Object sigmask );
}