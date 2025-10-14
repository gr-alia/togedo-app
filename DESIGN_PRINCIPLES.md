# Mobile App Design System Prompt for Claude Code

## Core Design Philosophy

You are tasked with planning and designing a modern mobile application using **Compose Multiplatform** following 2025 UI/UX design trends. The design must embody three fundamental principles:

1. **Clarity** - Every element has a clear purpose and is immediately understandable
2. **Effortless** - Interactions feel natural and require minimal cognitive load
3. **Human-centered** - The design adapts to user needs and feels approachable

## Primary Design Trends to Follow

### 1. Exaggerated Minimalism
- Use clean, minimalist backgrounds as the foundation
- Implement **oversized typography** for headlines and key actions
- Create **large, bold buttons** that are easy to tap (minimum 44dp touch targets)
- Use **generous white space** to create breathing room and visual hierarchy
- Apply **minimal color palette** (2-3 primary colors maximum)
- Remove all non-essential visual elements
- Make bold statements through scale and contrast, not decoration

### 2. Flat Design Evolution (Flat 2.0)
- Prioritize **2D, flat visual elements** with clean edges
- Use **bright, bold colors** without gradients (or very subtle gradients when necessary)
- Avoid textures, bevels, and heavy embellishments
- Implement **simple geometric shapes** and icons
- Use **subtle shadows ONLY when needed** for:
  - Floating action buttons (FAB)
  - Modal dialogs and overlays
  - Interactive cards that can be moved or dragged
  - Navigation elements that need to appear "lifted"
- Shadow specifications when used:
  - Blur: 4-8dp maximum
  - Opacity: 10-15% maximum
  - Offset: 0-2dp on Y-axis
  - Color: Use theme color at low opacity, not pure black
- For most cards and content: use **background color differentiation** (tonal elevation) without borders or shadows

### 3. Card-Based Design
- Organize content in **digestible, scannable cards**
- Use cards for grouping related information
- Implement **flat cards with background color differentiation** (no borders)
- Use **tonal elevation** to separate cards from background:
  - Light mode: Cards 2-5% lighter than background
  - Dark mode: Cards 2-5% lighter/brighter than background
- Reserve subtle shadows only for:
  - Interactive cards that can be swiped/moved
  - Cards that need hierarchical emphasis or interactivity
- Card specifications:
  - Border radius: 12-16dp for modern feel
  - Padding: 16-24dp internal spacing
  - Margins: 8-16dp between cards
  - Background: Solid color differentiated from base background, no gradients
  - No borders - rely on background color contrast for definition

### 4. Bold Typography & Variable Fonts
- Establish clear typographic hierarchy:
  - **Headlines**: 32-48sp, bold weight (700-800)
  - **Subheadings**: 20-24sp, semi-bold (600)
  - **Body text**: 16-18sp, regular (400)
  - **Captions**: 12-14sp, regular (400)
- Use **maximum 2 font families** (one for headings, one for body)
- Prefer sans-serif fonts for readability on mobile
- Implement **variable fonts** when possible for responsive scaling
- Use font weight variations to create hierarchy (not just size)
- Ensure minimum 1.5 line-height for body text (accessibility)

### 5. Gesture-Based Navigation
- Design for **swipe gestures** as primary navigation:
  - Horizontal swipes for moving between sections/tabs
  - Vertical scrolling for content consumption
  - Pull-to-refresh for updating content
  - Swipe-to-delete for list items
- Implement **slide animations** between screens (300-400ms duration)
- Use **carousel-style navigation** where appropriate
- Avoid complex, multi-level menu structures
- Design for **thumb-zone accessibility** (bottom 50% of screen)
- Include subtle visual cues for swipeable elements (e.g., indicators, partial reveals)

### 6. Bottom Navigation Bar
- Place **primary navigation at bottom** (3-5 items maximum)
- Design specifications:
  - Height: 56-64dp
  - Icons: 24dp x 24dp
  - Active state: Bold icon + color + label
  - Inactive state: Regular icon + muted color
  - Use micro-animations on tap (120-200ms)
- Never hide bottom navigation on scroll
- Ensure clear visual distinction between active and inactive states
- Include text labels (not just icons) for clarity

### 7. Micro-Interactions
- Add **subtle animations** for all interactive elements:
  - Button press: Scale down to 0.95 or subtle ripple effect
  - Toggle switches: Smooth slide with easing (200ms)
  - Like/favorite: Bounce or pop animation (300ms)
  - Loading states: Skeleton screens or subtle pulse
  - Success states: Checkmark animation or color flash
  - Form validation: Shake animation for errors
- Use **haptic feedback** for important actions (specify in design)
- Implement **progress indicators** for multi-step processes
- All animations should be:
  - Purposeful (communicate state or provide feedback)
  - Quick (150-400ms maximum)
  - Smooth (use easing functions, not linear)

## Design System Structure

### Color System
Define a minimal color palette:
- **Primary**: Main brand color (use for CTAs, active states)
- **Secondary**: Supporting color (use sparingly for accents)
- **Surface**: Background colors (2-3 shades for hierarchy)
- **Text**: High contrast text colors (primary at 87-100% opacity, secondary at 60% opacity)
- **Error/Success/Warning**: Semantic colors for feedback
- **Ensure WCAG AA contrast ratios** (4.5:1 for body text, 3:1 for large text)

### Elevation System
Create a simple elevation hierarchy using **tonal elevation** (background color changes) as the primary method:
- **Level 0 (Ground)**: Base background, no shadow, no border
- **Level 1 (Flat cards)**: 2-5% lighter/darker background (depending on theme), no shadow, no border
- **Level 2 (Raised elements)**: 4-8% lighter/darker background + optional 4dp blur shadow at 10% opacity
- **Level 3 (Overlay)**: 6-10% lighter/darker background + 6dp blur shadow at 12% opacity (modals, dialogs)

**Tonal Elevation Color Guidelines:**
- Light mode: Each level gets progressively lighter (add white)
- Dark mode: Each level gets progressively lighter/brighter (add white or primary color tint)
- Avoid borders - let color differentiation create separation

### Spacing System
Use an 8dp grid system:
- 4dp: Tight spacing (icons to text)
- 8dp: Default spacing (between related elements)
- 16dp: Section spacing (between groups)
- 24dp: Large spacing (between major sections)
- 32dp+: Extra large spacing (top margins, feature separation)

### Border Radius System
- Small: 8dp (buttons, inputs)
- Medium: 12dp (cards, containers)
- Large: 16dp (modals, bottom sheets)
- Extra large: 24dp (hero elements)
- Circle: 50% (avatars, FAB)

## Screen Layout Principles

### General Layout
- Use **edge-to-edge design** where appropriate
- Maintain **16-24dp side margins** for content
- Keep **main content in central 80% of screen width**
- Design for **one-hand usability** (place important actions in thumb reach)
- Use **full-bleed images** for visual impact

### Content Hierarchy
- Apply **F-pattern or Z-pattern** for content layout
- Place **most important action prominently** (oversized, high contrast)
- Use **visual weight** (size, color, position) to guide attention
- Group related items with **proximity and cards**
- Create **clear visual flow** from top to bottom

### Dark Mode Support
- Design both light and dark versions
- Dark mode specifications:
  - Use **true black (#000000) or near-black (#121212)** for backgrounds
  - Reduce white text to 87% opacity for comfort
  - Increase elevation through **lighter surfaces** (not darker shadows)
  - Adjust shadow opacity to 20-25% in dark mode
  - Use **desaturated colors** to reduce eye strain

## Interaction Patterns

### Touch Targets
- Minimum size: **44dp x 44dp** (Compose Multiplatform standard for both Android and iOS)
- Add visual feedback within **100ms** of touch
- Provide **clear active states** (color change, scale, or both)

### Loading States
- Use **skeleton screens** instead of spinners when possible
- Show **progress indicators** for actions taking >2 seconds
- Implement **optimistic UI** (show result immediately, undo if fails)

### Error Handling
- Use **inline validation** for forms (real-time feedback)
- Display errors with **clear, human-friendly messages**
- Include **suggested actions** to fix errors
- Use **color + icon + text** (never color alone for accessibility)

### Empty States
- Design **helpful empty states** with:
  - Simple illustration or icon
  - Clear explanation of why it's empty
  - Suggested action to populate content
  - Maintain exaggerated minimalism (not cluttered)

## Accessibility Requirements

- Ensure **minimum 4.5:1 contrast ratio** for all text
- Support **dynamic type sizing** (users can increase text size)
- Design for **VoiceOver/TalkBack** screen readers
- Include **clear focus indicators** for keyboard navigation
- Never rely on **color alone** to convey information
- Test with **grayscale filter** to verify hierarchy works without color

## Technical Specifications for Developers

When handing off to Compose Multiplatform developers, include:
- Component spacing in **dp units** (density-independent pixels)
- Font sizes in **sp units** (scalable pixels for text)
- Color values in **HEX and RGB**, or Compose Color format
- Animation duration and **easing functions** (e.g., EaseInOut, FastOutSlowIn)
- Touch target sizes and **tap areas** in dp
- Gesture directions and **sensitivity thresholds**
- Font weights, sizes (sp), and **line heights**
- Shadow specifications: **X-offset (dp), Y-offset (dp), blur (dp), spread (dp), color, opacity**
- States for each interactive component: **default, hover, active, disabled, loading**
- Use Compose Modifier chains for styling (padding, size, background, etc.)

## Design Deliverables

Provide:
1. **Low-fidelity wireframes** showing layout and flow
2. **High-fidelity mockups** for key screens (light and dark mode)
3. **Component library** with all reusable elements
4. **Interaction specifications** describing animations and gestures
5. **Responsive behavior** notes for different screen sizes
6. **User flow diagrams** showing navigation paths
7. **Design system documentation** with all tokens and guidelines

## Key Principles Summary

**DO:**
- ✅ Use flat design with minimal shadows
- ✅ Implement oversized typography and buttons
- ✅ Design for gestures and swipes
- ✅ Add purposeful micro-interactions
- ✅ Use card-based layouts with background color differentiation
- ✅ Place navigation at bottom
- ✅ Create generous white space
- ✅ Support both light and dark modes
- ✅ Design for thumb-zone accessibility
- ✅ Use tonal elevation (color changes) to create depth

**DON'T:**
- ❌ Overuse shadows or gradients
- ❌ Create complex multi-level menus
- ❌ Use small, hard-to-tap targets
- ❌ Add decorative elements without purpose
- ❌ Implement slow or unnecessary animations
- ❌ Hide navigation elements
- ❌ Overcrowd the interface
- ❌ Rely on color alone for information
- ❌ Ignore accessibility standards
- ❌ Use borders on cards (use background color differentiation instead)

## Design Thinking Process

When planning the app design:
1. **Understand the user problem** - What are users trying to accomplish?
2. **Map user journeys** - How will users flow through the app?
3. **Prioritize ruthlessly** - What can be removed while maintaining function?
4. **Design for the primary action** - Make the most important thing the most obvious
5. **Iterate on clarity** - If something requires explanation, redesign it
6. **Test for effortlessness** - Can users accomplish tasks in the fewest steps?
7. **Validate human-centeredness** - Does it feel welcoming and understandable?

---

**Remember**: Every design decision should serve the user's goals. If an element doesn't add clarity, make interaction more effortless, or enhance the human-centered experience, remove it. The goal is not to be trendy, but to create an interface so intuitive that it becomes invisible—allowing users to focus on their tasks, not the interface itself.
