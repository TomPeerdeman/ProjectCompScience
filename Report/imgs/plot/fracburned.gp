set terminal pdfcairo
set grid
set output "../fracburned.pdf"
set key right bottom
set xlabel "Density"
set ylabel "Fraction burned"
plot "fracburned0.dat" using 1:2 with points title "Std grid", "fracburned1.dat" using 1:2 with points title "Hex grid", "fracburned2.dat" using 1:2 with points title "Triangle grid"
