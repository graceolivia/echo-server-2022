#!/bin/sh

 set -Eeou pipefail

 function checkForCheckstyle {
   command -v checkstyle >/dev/null 2>&1
 }

 function printNoCheckstyleAndExit {
   echo "You don't have checkstyle installed. You can get it with \`brew install checkstyle\`"
   exit 1
 }

 function check {
   echo "----- \nStarting Checkstyle..."
   checkstyle -c checkstyleChecks.xml src/main/ >/dev/null 2>&1
 }

 function printCheckFailed {
   echo "----- \nCheckstyle found errors. Commit cancelled."
   echo "To see errors, run: \ncheckstyle -c checkstyleChecks.xml src/main/"
   echo "-----"
   exit 1
 }

 checkForCheckstyle || printNoCheckstyleAndExit

 check || printCheckFailed

 echo "----- \nCheckstyle complete!\n-----"