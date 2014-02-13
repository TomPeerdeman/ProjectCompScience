set terminal pdfcairo
set grid
set output "../oppreached.pdf"
set key right bottom
set xlabel "Density"
set ylabel "Fraction reached"
plot "oppreached0.dat" using 1:2 with points title "Std grid", "oppreached1.dat" using 1:2 with points title "Hex grid", "oppreached2.dat" using 1:2 with points title "Triangle grid"
