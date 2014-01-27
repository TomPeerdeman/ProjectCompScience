BIB = bibtex
TEX = pdflatex

FILE = Forest_fire_paper

$(FILE).pdf: $(FILE).bbl
	$(TEX) $(FILE)
	$(TEX) $(FILE)

$(FILE).bbl: $(FILE).bib $(FILE).aux
	$(BIB) $(FILE).aux
	
$(FILE).aux:
	$(TEX) $(FILE)
	rm -f out
	
.PHONY: clean bib pdf cleanbib

clean:
	rm -f $(FILE).pdf *.aux *.bbl *.blg *.out *.log
	
bib: $(FILE).bbl
	
pdf: $(FILE).bbl
	$(TEX) $(FILE)
	$(TEX) $(FILE)
	
cleanbib:
	rm -f *.bbl *.blg
