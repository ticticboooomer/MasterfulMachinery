// src/modules/mm/startup.js
onEvent("mm.controllers", function(event) {
  event.create("test").name("My Controller").build();
});
onEvent("mm.ports", function(event) {
  event.create("test_input").name("My Port").input(true).port("mm:item").config({
    slotRows: 3,
    slotCols: 3
  }).build();
  event.create("test_output").name("My Port").input(false).port("mm:item").config({
    slotRows: 2,
    slotCols: 2
  }).build();
});
