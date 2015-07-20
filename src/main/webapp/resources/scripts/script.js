(function () {
    'use strict';

    function validateForm (data) {
        if (data === "-1") {
            return false;
        }
        return true;
    }

    function generateContenderFields (totalContenders, poolSelect) {

        var contendersEachPool = Math.ceil(totalContenders / poolSelect);

        var total = 0;
        for (var i = 0; i < contendersEachPool; i++) {
            for (var j = 0; j < poolSelect; j++) {
                if (total === totalContenders) {
                    break;
                }
                var currentPool = "#pool" + (j + 1);
                $(currentPool).append('<div class="form-group pool-field"><div class="col-md-12"><input type="text" class="form-control contender-input" name="contender-' + (i + j) + '" id="contender-' + (i + j) + '"placeholder="Contender">' + '</div></div>');
                total++;

            }
        }
    }

    $(document).ready(function () {

        var url, data, i, pools, contenders, selectedGender, totalContenders, formArray, request, poolSelect, poolsWrapper, currentFight;
        url = window.location + "Load";

        data = $.ajax({
        type : "GET",
        async : false,
        url : url,
        contentType : 'application/json',
        success : function (data) {

            pools = $('#pool-select');
            for (i = 1; i < data.maxPools + 1; i++) {
                $(pools).append('<option value=' + i + '>' + i + '</option>');
            }

            return data;
        },
        error : function (x, textStatus, errorThrown) {
            console.log(x);
            console.log(textStatus);
            console.log(errorThrown);
        }
        });

        $('#gender-select').change(function () {

            $(this).parent().removeClass('has-error has-feedback');
            $("#gender-error-text").remove();

            selectedGender = $(this).val();
            $("#weight-select").empty();

            if (selectedGender === "-1") {
                $("#weight-select").append('<option value=' + -1 + '>--</option>');
            } else if (selectedGender === "male") {
                for (i = 0; i < data.responseJSON.maleWeightClasses.length; i++) {
                    $("#weight-select").append('<option value=' + data.responseJSON.maleWeightClasses[i] + '>' + data.responseJSON.maleWeightClasses[i] + ' kg</option>');
                }
            } else if (selectedGender === "female") {
                for (i = 0; i < data.responseJSON.femaleWeightClasses.length; i++) {
                    $("#weight-select").append('<option value=' + data.responseJSON.femaleWeightClasses[i] + '>' + data.responseJSON.femaleWeightClasses[i] + ' kg</option>');
                }
            }
        });

        $('#pool-select').change(function () {

            $(this).parent().removeClass('has-error has-feedback');
            $("#pools-error-text").remove();

            totalContenders = parseInt($('#contender-select').val());
            poolSelect = $(this).val();
            contenders = $('#contender-select');
            contenders.empty();
            contenders.append('<option value=' + -1 + ' id="contender-option-0">--</option>');

            if (poolSelect == "-1") {
                $('#pools-row').hide();
                $('#pools-row').empty();
            } else {

                for (i = (poolSelect * 2); i < data.responseJSON.maxContender + 1; i++) {
                    $(contenders).append('<option value=' + i + ' id="contender-option-' + i + '">' + i + '</option>');
                }

                if (poolSelect == 1) {
                    $('#pools-row').empty();
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool1"><h3>Pool 1</h3></div>');
                } else if (poolSelect == 2) {
                    $('#pools-row').empty();
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool1"><h3>Pool 1</h3></div>');
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool2"><h3>Pool 2</h3></div>');
                } else if (poolSelect == 3) {
                    $('#pools-row').empty();
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool1"><h3>Pool 1</h3></div>');
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool2"><h3>Pool 2</h3></div>');
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool3"><h3>Pool 3</h3></div>');
                } else if (poolSelect = 4) {
                    $('#pools-row').empty();
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool1"><h3>Pool 1</h3></div>');
                    $('#pools-row').append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool2"><h3>Pool 2</h3></div>');
                    $('#pools-row').last().append('<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 pool" id="pool3"><h3>Pool 3</h3></div>');
                    $('#pools-row').last().append('<div class="col-xs-12 col-sm-offset-2 col-sm-8  col-md-offset-3 col-md-6 pool" id="pool4"><h3>Pool 4</h3></div>');
                }

                generateContenderFields(totalContenders, poolSelect);
                if (totalContenders === -1) {
                    totalContenders = 0;
                }
                $('#contender-option-' + totalContenders).attr('selected', 'selected');
            }

        });

        $('#contender-select').change(function () {

            poolSelect = $('#pool-select').val();

            $('.pool-field').remove();
            totalContenders = parseInt($(this).val());

            if (totalContenders !== -1) {

                $(this).parent().removeClass('has-error has-feedback');
                $("#contender-error-text").remove();

                generateContenderFields(totalContenders, poolSelect);
            }
        });

        $('#submit-form').submit(function (event) {

            formArray = $(this).serializeArray();

            if (formArray[0].value === null || formArray[0].value === "") {
                $('#tournament-error-text').remove();
                $('#tournament-name').parent().addClass('has-error has-feedback').append('<span class="glyphicon glyphicon-remove form-control-feedback"></span>');
                $('#tournament-name').parent().append('<section class="row"><div class="col-xs-12 col-md-12" id="tournament-error-text"><h5>You must enter Tournament name</h5></div></section>');
                return false;
            }
            if (!validateForm(formArray[1].value)) {
                $('#gender-error-text').parent().remove();
                $('#gender-select').parent().addClass('has-error has-feedback');
                $('#gender-select').parent().append('<section class="row"><div class="col-xs-12 col-md-12" id="gender-error-text"><h5>You must enter a gender</h5></div>');
                return false;
            }
            if (!validateForm(formArray[4].value)) {
                $('#contender-error-text').parent().remove();
                $('#contender-select').parent().addClass('has-error has-feedback');
                $('#contender-select').parent().append('<section class="row"><div class="col-xs-12 col-md-12" id="contender-error-text"><h5>You must select how many contenders</h5></div>');
                return false;
            }
            
            for (var i = 5; i < formArray.length; i++) {
                
                if(formArray[i].value === null || formArray[i].value === ""){
                    $('#contender-error-text').parent().remove();
                    var currentContenderInput = $('#contender-' + (i-5));
                    var error = '<section class="row"><div class="col-xs-12 col-md-12" id="input-error-text"><h5>You must enter a contender</h5></div></section>';
                    currentContenderInput.parent().addClass('has-error has-feedback');
                    currentContenderInput.parent().append(error);
                    return false;
                }
                
            }
            
            var pools = $('#pool-select').val();

            contenders = [];
            var k = 5;
            for (i = 0; i < pools; i++) {
                var inputFields = $('#pool' + (i + 1) + " input");
                var pool = [];
                for (var j = 5; j < (inputFields.length + 5); j++) {
                    pool.push(formArray[k++].value);
                }
                contenders.push(pool);
            }

            var weightString = formArray[2].value;
            var weightInt = parseInt(weightString.substring(1, weightString.length));

            request = {
            tournamentName : formArray[0].value,
            gender : formArray[1].value,
            weight : weightInt,
            pools : parseInt(formArray[3].value),
            totalContenders : parseInt(formArray[4].value),
            contenders : contenders
            };

            $.ajax({
            type : "POST",
            async : false,
            url : window.location + "Sort",
            contentType : 'application/json;charset=UTF-8',
            data : JSON.stringify(request),
            dataType : "json",
            success : function (data) {

                $('form').toggle();
                $('#info-tournament-name').append('<h4>' + data.tournamentName + '</h4>');
                $('#info-gender').append('<h4>' + data.gender + '</h4>');
                $('#info-weight').append('<h4>-' + data.weight + ' kg</h4>');
                $('#info-pools').append('<h4>' + data.pools + '</h4>');
                $('#info-contenders').append('<h4>' + data.totalContenders + '</h4>');
                $('#info-wrapper').toggle();

                for (i = 0; i < data.fighters.length; i++) {
                    var currentPool = $('#pool' + (i + 1) + '-fights');
                    for (j = 0; j < data.fighters[i].length; j++) {
                        currentFight = '<section class="row"><div class="col-xs-12"><h4>' + data.fighters[i][j].first + " - " + data.fighters[i][j].second + '</h4></div></section>';
                        currentPool.append(currentFight);
                    }
                    currentPool.toggle();
                }
            },
            error : function (x, textStatus, errorThrown) {
                console.log(x);
                console.log(textStatus);
                console.log(errorThrown);
            }
            });

            console.log(JSON.stringify(request));

            event.preventDefault();

        });

        $("#tournament-name").on('focus', function () {

            $(this).parent().removeClass('has-error has-feedback');
            $(this).parent().children().remove("span");
            $("#tournament-error-text").remove();
        });
        
        $('body').on('focus', ".contender-input", function () {

            $(this).parent().removeClass('has-error has-feedback');
            $(this).parent().children().remove("span");
            $("#input-error-text").remove();
        });
    });
}());