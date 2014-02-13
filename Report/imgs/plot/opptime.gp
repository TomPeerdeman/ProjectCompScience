set terminal pdfcairo
set grid
set output "../opptime.pdf"
set key right top
set xlabel "Density"
set ylabel "Time"
plot "opptime0.dat" using 1:2 with points title "Std grid", "opptime1.dat" using 1:2 with points title "Hex grid", "opptime2.dat" using 1:2 with points title "Triangle grid"
