# Togedo — Color System

## Concept

The palette is built around the metaphor of **two partners**. Every color has a clear role and owner. Berry belongs to the user, Dusty Purple belongs to the partner. When both appear together, that is closeness.

---

## Primary Pair

| Token | Name | Hex | Role |
|---|---|---|---|
| `primary` | Deep Berry | `#C2456A` | CTA buttons, FAB (+), active nav tab, user's plan cards, onboarding header |
| `onPrimary` | White | `#FFFFFF` | Text and icons placed on top of primary |
| `secondary` | Dusty Purple | `#7B5EA7` | Partner's plan cards, online dot, category tags, partner's ideas, checkboxes |
| `onSecondary` | White | `#FFFFFF` | Text and icons placed on top of secondary |

---

## Backgrounds

| Token | Name | Hex | Role |
|---|---|---|---|
| `background` | Ivory Blush | `#FAF7F2` | All screen backgrounds, navigation bar |
| `onBackground` | Near Black | `#1E1218` | Primary text placed on background |
| `surface` | White | `#FFFFFF` | Card surfaces |
| `onSurface` | Near Black | `#1E1218` | Primary text placed on surface |
> Cards use **tonal elevation** (background color differentiation) — no borders. The `outline` token is reserved for input fields and dividers only.

| `primaryContainer` | Blush Tint | `#FAEAEE` | Berry tags, light idea cards, Berry hover states |
| `secondaryContainer` | Lavender Tint | `#F0EBF8` | Purple tags, light idea cards, Purple hover states |

---

## Typography

| Token | Name | Hex | Usage |
|---|---|---|---|
| `text` | Near Black | `#1E1218` | Headings, activity names, main content |
| `textSecondary` | Deep Rose | `#4A2C38` | Dates, times, metadata |
| `onSurfaceVariant` | Muted Rose | `#7A5060` | Labels, placeholders, section headers |

---

## Text Tokens

Use these tokens for all text color assignments in Composables. Do not use `onBackground` or `onSurface` directly for text.

| Token | Light | Dark | Usage |
|---|---|---|---|
| `text` | `#1E1218` | `#F0E6F0` | All primary body text |
| `textSecondary` | `#4A2C38` | `#DBC8D8` | Dates, metadata, supporting text |
| `textDisabled` | `#7A5060` | `#A890A8` | Placeholder text, disabled labels |

---

## Accent

| Token | Name | Hex | Role |
|---|---|---|---|
| `tertiary` | Earth Yellow | `#EFB46E` | Partner avatars, streak progress dots, "whose idea" micro-detail on cards |
| `onTertiary` | Dark Brown | `#3D2200` | Text and icons placed on top of tertiary |

> Earth Yellow appears only in micro-details — never as a card or button color competing with the primary pair.

---

## Borders & Dividers

| Token | Hex | Usage |
|---|---|---|
| `outline` | `#E8E0D8` | Card borders, dividers, input fields |
| `outlineVariant` | `#F0EAE4` | Subtle separators, nav border-top |

---

## Dark Theme

Same tokens, different values. The component always references the same token — the theme resolves it to the correct hex.

### Primary / Secondary (dark)

| Token | Name | Hex | Note |
|---|---|---|---|
| `primary` | Soft Berry | `#F4A0BC` | Lighter — readable on dark background |
| `onPrimary` | Deep Dark | `#1A1220` | Dark text on light primary button |
| `secondary` | Soft Purple | `#C5AEE8` | Lighter — readable on dark background |
| `onSecondary` | Deep Dark | `#1A1220` | Dark text on light secondary button |

### Containers — dark (plan cards)

| Token | Name | Hex | Note |
|---|---|---|---|
| `primaryContainer` | Deep Berry Dark | `#3A1828` | Dark tinted surface for user's plan cards |
| `onPrimaryContainer` | Soft Berry | `#F4A0BC` | Accent text on primaryContainer |
| `secondaryContainer` | Deep Purple Dark | `#251838` | Dark tinted surface for partner's plan cards |
| `onSecondaryContainer` | Soft Purple | `#C5AEE8` | Accent text on secondaryContainer |

### Tertiary — dark

| Token | Name | Hex | Note |
|---|---|---|---|
| `tertiary` | Warm Yellow | `#F5C878` | Slightly brighter for dark bg readability |
| `onTertiary` | Deep Dark | `#1A1220` | Dark text on tertiary |

### Background / Surface — dark

| Token | Name | Hex | Note |
|---|---|---|---|
| `background` | Deep Plum | `#1A1220` | Main screen background — dark with berry-purple undertone |
| `onBackground` | Soft Blush | `#F0E6F0` | Primary text — warm near-white, not cold |
| `surface` | Dark Surface | `#241828` | Card surfaces — one step lighter than background |
| `onSurface` | Warm Lilac | `#DBC8D8` | Secondary text on surface |
| `onSurfaceVariant` | Muted Lilac | `#A890A8` | Labels, placeholders, section headers |

### Borders & Dividers — dark

| Token | Hex | Note |
|---|---|---|
| `outline` | `#3A2840` | Card borders, input outlines |
| `outlineVariant` | `#2C2035` | Subtle separators, nav border-top |

---

## Semantic Colors

### Error — warm raspberry

| Token | Light | Dark | Note |
|---|---|---|---|
| `error` | `#B5274A` | `#F2849C` | Warm raspberry — berry undertone, reads as "stop" |
| `onError` | `#FFFFFF` | `#1A1220` | Text on error |
| `errorContainer` | `#FCEEF2` | `#4A1028` | Input fill, error chip background |
| `onErrorContainer` | `#7A1030` | `#F2849C` | Text on errorContainer |

### Success — muted sage green

| Token | Light | Dark | Note |
|---|---|---|---|
| `success` | `#3D7A52` | `#7EC89A` | Earthy sage — warm, not acidic |
| `onSuccess` | `#FFFFFF` | `#1A1220` | Text on success |
| `successContainer` | `#EAF4EE` | `#0E3020` | Toast background, success chip |
| `onSuccessContainer` | `#1E4A30` | `#7EC89A` | Text on successContainer |

### Disabled — warm rose-grey

| Token | Light | Dark | Note |
|---|---|---|---|
| `disabled` | `#C8B8C0` | `#4A3A42` | Warm rose-grey — not cold neutral |
| `onDisabled` | `#FFFFFF` | `#7A6870` | Text on disabled button |
| `disabledContainer` | `#EDE6E8` | `#302028` | Disabled input/card background |
| `onDisabledContainer` | `#A898A0` | `#6A5860` | Text on disabledContainer |

---

## Feature-Specific: Activity Status Colors

| Token | Light | Dark | Status |
|---|---|---|---|
| `featureSpecificStatusPlanning` | `#EDE6E8` | `#302028` | Idea / draft state |
| `onFeatureSpecificStatusPlanning` | `#1E1218` | `#F0E6F0` | |
| `featureSpecificStatusPlanned` | `#F0EBF8` | `#251838` | Confirmed plan |
| `onFeatureSpecificStatusPlanned` | `#7B5EA7` | `#C5AEE8` | |
| `featureSpecificStatusCanceled` | `#FCEEF2` | `#4A1028` | Canceled |
| `onFeatureSpecificStatusCanceled` | `#B5274A` | `#F2849C` | |
| `featureSpecificStatusDone` | `#EAF4EE` | `#0E3020` | Completed |
| `onFeatureSpecificStatusDone` | `#3D7A52` | `#7EC89A` | |

---

## Using Colors in Compose

Always access colors via `AppTheme.colors.*` in Composables. Never hardcode hex values directly in UI code.

```kotlin
// Correct
Text(color = AppTheme.colors.text)
Box(modifier = Modifier.background(AppTheme.colors.primaryContainer))

// Wrong
Text(color = Color(0xFF1E1218))
Box(modifier = Modifier.background(DeepBerry))
```

---

## Usage Rules

### Berry `#C2456A` is used for:
- Primary CTA buttons ("Запропонувати Владу", "Запланувати")
- Floating Action Button (+)
- Active state of bottom navigation
- User's own plan cards
- Onboarding screen header background
- Active input field border

### Dusty Purple `#7B5EA7` is used for:
- Partner's plan cards
- Online indicator dot
- Category and filter tags
- Idea cards attributed to the partner
- Checkboxes and toggles (checked state)

### Ivory Blush `#FAF7F2` is used for:
- Background of all screens
- Navigation bar background
- Bottom sheet background

### Earth Yellow `#EFB46E` is used for:
- Partner avatar fill
- Streak / progress dots (filled state)
- Small "whose idea" dot on plan cards

### Never do:
- Do not use Earth Yellow as a card background or button color
- Do not use TextPrimary (`#1E1218`) directly on Berry or Purple backgrounds — use `#FFFFFF` instead
- Do not mix Berry and Purple on the same card (they identify different partners)

---

## Color Hierarchy (60 / 30 / 10)

| Layer | Color | Approx. share |
|---|---|---|
| Background & surfaces | Ivory Blush + Card White | ~60% |
| Primary + Secondary | Berry + Dusty Purple | ~30% |
| Accent | Earth Yellow | ~10% |

---

## Brand Context

**UVP:** Togedo resolves the feeling of "we are together but somehow distant" by creating a shared space where it is easy to initiate moments of closeness and care.

**Brand identity:** The user feels like the best, most caring partner.

**Tone:** Calming, warm, intimate — not utilitarian.

**Color metaphor:** Berry (you) + Dusty Purple (partner) appearing together = closeness.
