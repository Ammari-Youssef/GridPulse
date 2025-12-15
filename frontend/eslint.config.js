// @ts-check
const eslint = require("@eslint/js");
const { defineConfig } = require("eslint/config");
const tseslint = require("typescript-eslint");
const angular = require("angular-eslint");

module.exports = defineConfig([
  {
    files: ["**/*.ts"],
    extends: [
      eslint.configs.recommended,
      tseslint.configs.recommended,
      tseslint.configs.stylistic,
      angular.configs.tsRecommended,
    ],
    processor: angular.processInlineTemplates,
    rules: {
      "@angular-eslint/directive-selector": [
        "error",
        {
          type: "attribute",
          prefix: "app",
          style: "camelCase",
        },
      ],
      "@angular-eslint/component-selector": [
        "error",
        {
          type: "element",
          prefix: "app",
          style: "kebab-case",
        },
      ],
      
      // STOP standalone forcing
      "@angular-eslint/prefer-standalone": "off",
      "@angular-eslint/prefer-standalone-component": "off",

      // STOP inject() forcing
      "@angular-eslint/prefer-inject": "off",
      "@angular-eslint/prefer-injectable-provided-in": "off",
      "@angular-eslint/use-injectable-provided-in": "off",

      // STOP noise
      "@typescript-eslint/no-unused-vars": "off",
    },
  },
  {
    files: ["**/*.html"],
    extends: [
      angular.configs.templateRecommended,
      angular.configs.templateAccessibility,
    ],
    rules: {
      "@angular-eslint/directive-selector": [
        "error",
        { type: "attribute", prefix: "app", style: "camelCase" }
      ],
      "@angular-eslint/component-selector": [
        "error",
        { type: "element", prefix: "app", style: "kebab-case" }
      ],

      // Disable rules enforcing inject() or standalone patterns
      "@angular-eslint/prefer-injectable-provided-in": "off",
      "@angular-eslint/prefer-standalone-component": "off",
      "@angular-eslint/use-injectable-provided-in": "off",
      "@angular-eslint/prefer-inject": "off",

      // Disable unused vars
      "@typescript-eslint/no-unused-vars": "off",
    }
  }
]);
