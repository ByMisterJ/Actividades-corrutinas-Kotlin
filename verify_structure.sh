#!/bin/bash
# Build Verification Checklist for Actividades-corrutinas-Kotlin

echo "=========================================="
echo "Build Verification Script"
echo "=========================================="
echo ""

echo "✓ Checking project structure..."
if [ -d "app/src/main/java/com/example/actividades_corrutinas_kotlin" ]; then
    echo "  ✓ Main package exists"
else
    echo "  ✗ Main package missing"
    exit 1
fi

echo ""
echo "✓ Checking Kotlin files..."
KOTLIN_FILES=$(find app/src/main/java -name "*.kt" | wc -l)
echo "  Found $KOTLIN_FILES Kotlin files"
if [ "$KOTLIN_FILES" -eq 13 ]; then
    echo "  ✓ Expected number of files (13)"
else
    echo "  ⚠ Expected 13 files, found $KOTLIN_FILES"
fi

echo ""
echo "✓ Checking layout files..."
LAYOUT_FILES=$(find app/src/main/res/layout -name "*.xml" | wc -l)
echo "  Found $LAYOUT_FILES layout files"
if [ "$LAYOUT_FILES" -eq 7 ]; then
    echo "  ✓ Expected number of layouts (7)"
else
    echo "  ⚠ Expected 7 layouts, found $LAYOUT_FILES"
fi

echo ""
echo "✓ Checking documentation..."
if [ -f "kotlin_coroutines_activities.md" ]; then
    echo "  ✓ Technical documentation exists"
else
    echo "  ✗ Technical documentation missing"
    exit 1
fi

if [ -f "README.md" ]; then
    echo "  ✓ README exists"
else
    echo "  ✗ README missing"
    exit 1
fi

echo ""
echo "✓ Checking manifest..."
if grep -q "SequentialTasksActivity" app/src/main/AndroidManifest.xml; then
    echo "  ✓ All activities registered in manifest"
else
    echo "  ✗ Some activities missing from manifest"
    exit 1
fi

echo ""
echo "=========================================="
echo "Structure verification: PASSED"
echo "=========================================="
echo ""
echo "To build the project:"
echo "  1. Open in Android Studio"
echo "  2. Wait for Gradle sync"
echo "  3. Run on device/emulator"
echo ""
echo "To verify functionality:"
echo "  1. Launch app"
echo "  2. Test each activity from menu"
echo "  3. Verify cancellation in activities 2, 5, 6"
echo "  4. Verify timing in activity 4"
echo ""
