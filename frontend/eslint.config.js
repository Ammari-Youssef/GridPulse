// @ts-check
const eslint = require("@eslint/js");
const tseslint = require("typescript-eslint");
const angular = require("angular-eslint");

module.exports = tseslint.config(
  {
    files: ["**/*.ts"],
    extends: [
      eslint.configs.recommended,
      ...tseslint.configs.recommended,
      ...tseslint.configs.stylistic,
      ...angular.configs.tsRecommended,
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
      // legacy-friendly architectural choices
      // STOP standalone forcing
      "@angular-eslint/prefer-standalone": "off",
      "@angular-eslint/prefer-standalone-component": "off",
      // STOP inject() forcing
      "@angular-eslint/prefer-inject": "off",
      "@angular-eslint/prefer-injectable-provided-in": "off",
      "@angular-eslint/use-injectable-provided-in": "off",
      // Code quality hints
      "@typescript-eslint/no-unused-vars": "warn",
      "@typescript-eslint/no-explicit-any": "warn",
    },
  },
  {
    files: ["**/*.html"],
    extends: [
      ...angular.configs.templateRecommended,
      ...angular.configs.templateAccessibility,
    ],
    rules: {
      // Legacy-friendly: Allow *ngIf, *ngFor instead of @if, @for
      "@angular-eslint/template/prefer-control-flow": "off",
      "@angular-eslint/template/use-track-by-function": "off",
      
      // Accessibility rules adjustments
      "@angular-eslint/template/click-events-have-key-events": "off",
      "@angular-eslint/template/label-has-associated-control": "off",
      "@angular-eslint/template/interactive-supports-focus": "off",
      
    },
  }
);
