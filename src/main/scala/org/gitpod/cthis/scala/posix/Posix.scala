package org.gitpod.cthis.scala.posix

import com.sun.jna.Library

/**
 * <p> JNAing POSIX's pselect => making it loadable by JNA "Native" </p>
 *
 * @author anatoly.polinsky
 */
trait Posix extends Library {

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
    def pselect ( nfds: Int,
                  readfds: AnyRef,
                  writefds: AnyRef,
                  exceptfds: AnyRef,
                  timeout: TimeSpec,
                  sigmask: AnyRef ): Int
}
