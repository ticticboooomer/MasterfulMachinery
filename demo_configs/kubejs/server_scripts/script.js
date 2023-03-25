// src/modules/mm/server.js
onEvent("mm.structures", function(event) {
  event.build("test:forge_hammer", {
    name: {
      text: "Primitive Forge Hammer"
    },
    controllerId: "mm:test",
    layout: [
      [
        "ACB"
      ]
    ],
    key: {
      A: {
        type: "mm:port",
        port: "mm:item",
        input: true
      },
      B: {
        type: "mm:port",
        port: "mm:item",
        input: false
      },
      E: {
        type: "mm:tag",
        tag: "forge:stone"
      }
    }
  });
});
onEvent("mm.recipes", function(event) {
  event.build("test:hammer_tings", {
    duration: 20,
    structureId: "test:forge_hammer",
    name: {
      text: "Hammer Time"
    },
    inputs: [
      {
        type: "mm:simple",
        ingredient: {
          type: "mm:item",
          count: 2,
          item: "minecraft:stick"
        }
      }
    ],
    outputs: [
      {
        type: "mm:simple",
        ingredient: {
          type: "mm:item",
          item: "minecraft:dirt",
          count: 1
        }
      }
    ]
  });
});
