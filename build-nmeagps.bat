pushd nmeagps
call mvn package
popd
copy nmeagps\target\*.jar gpsvisualizer\code
