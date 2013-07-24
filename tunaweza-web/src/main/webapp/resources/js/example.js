$(function() {

    var numCols = $("colgroup").length,
        featuredCol;
    
    // Zebra striping
    $("tr:odd").addClass("odd");
    $("tr:last").addClass("final-row");
    
    // Figure out which column # is featured.
    $("colgroup").each(function(i) {
        if (this.id == "featured") featuredCol = i+1;
    });
    
    // Apply classes to each table cell indicating column
    // Also applies classes if cell is right or left of featured column
    $("td, th").each(function(i) {
        $(this).addClass("table-col-" + ((i % numCols) + 1));
        if (((i%numCols)+1) == (featuredCol-1)) $(this).addClass("leftOfFeatured");
        if (((i%numCols)+1) == (featuredCol+1)) $(this).addClass("rightOfFeatured");
    });
        
});