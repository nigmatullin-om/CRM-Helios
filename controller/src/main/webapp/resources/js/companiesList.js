$(document).ready(function () {
    var byTaskType='all';
    var byPeriod='allTime';
    var chosenDate='';
    var createdOrModified='created';
    var byStages=[];
    var byUser='';
    var byTask='';
    var byTag='';

    $(':radio[name="byTaskOption"]').change(function () {
        byTaskType = $(':radio[name="byTaskOption"]:checked').val();
    });

    $('#period-dropdown').change(function() {
        $('#select-period option:gt(6)').remove();
        byPeriod = $(this).val();
        $.datepicker.setDefaults( $.datepicker.regional[ "ru" ]);
        if (byPeriod == 'calendar') {
            $("#datepicker").show();
            $("#datepicker").datepicker({
                onSelect : function(dateText) {
                    chosenDate = dateText;

                    $("#period-dropdown").append($("<option/>", {
                        text: dateText,
                        id: 'date',
                        selected: true
                    }));
                    $("#datepicker").hide();
                }
            });
        }
    });

    $(':radio[name="byPeriodOption"]').change(function () {
        createdOrModified = $(':radio[name="byPeriodOption"]:checked').val();
    });

    var $checkboxes = $("input:checkbox");
    $checkboxes.change(function() {
        byStages = $('input:checkbox:checked').map(function() {
            return $(this).val();
        }).get();
    });

    $('#managers-dropdown').change(function() {
        byUser = $(this).val();
    });

    $('#tasks-dropdown').change(function() {
        byTask = $(this).val();
    });

    $('#tags-dropdown').change(function() {
        byTag = $(this).val();
    });

    $("#reset").click(function() {
        $('#radio-task')[0].reset();
        $('#select-period')[0].reset();
        $('#select-period option:gt(6)').remove();
        $('#radio-created')[0].reset();
        $('#checkbox-stage')[0].reset();
        $('#select-managers')[0].reset();
        $('#select-task')[0].reset();
        $('#select-tags')[0].reset();

        byTaskType='all';
        byPeriod='allTime';
        chosenDate='';
        createdOrModified='created';
        byStages=[];
        byUser='';
        byTask='';
        byTag='';
    });

    $("#submit").click(function(){
        $.post("http://localhost:8080/crm-helios/view/CompaniesList",
            {
                byTaskType: byTaskType,
                byPeriod: byPeriod,
                chosenDate: chosenDate,
                createdOrModified: createdOrModified,
                byStages: byStages,
                byUser: byUser,
                byTask: byTask,
                byTag: byTag
            },
            function(responseJson){
                $('#tablebody').empty();
                $.each(responseJson, function(index, companies) {
                    var row = ('<tr><td>'+companies.name+'</td><td class="text-center">'+companies.phone+'</td><td class="text-center">'+companies.email+'</td></tr>');
                    $('#tablebody').append(row);
                });
            });
    });

});
