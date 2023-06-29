# Drawing_app
Android app that let user draw freehand shapes on canvas using threads. Application is written in Java and Android Studio (Electric Eel 2022.1.1) and features:
- Drawing line color change - after pressing one of three buttons (cyan/magenta/yellow) the next line color will be changed to corresponding color,
- Clearing canvas - the Drawing Surface will be cleaned after pressing grey button with eraser icon with respectful animation,
- Clear option - after going into up right corner in the pulpet menu if choosing "Clear" the canvas will be cleared,
- Motion Layout - animation during color change as well as clearing canvas if using eraser button,
- Surface View - class that provides functionality to draw on surface.

# Development environment:

- Java SE Runtime Environment (build 1.8.0_371-b11)
- Android Studio Electric Eel | 2022.1.1

# How to compile and run
To run application:

- Download zip package
- Extract package and open using Android Studio
- If there is error with versions (pre Electric Eel) you should change version of the IDE in one of the gradle files and rebuild
- Build application and run (either on VM Android or physical device, the development device is Samsung A53)

# Screenshots:
1. App tray icon:

<table>
    <tr>
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/1_app_tray.png" width="200"/>
        </td>     
    </tr>
</table>

2. First launch and default color:

<table>
    <tr>
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/2_first_launch.png" width="200"/>
        </td>  
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/3_default_white_line.png" width="200"/>
        </td>   
    </tr>
</table>
3. Color change (cyan,magenta,yelllow) and drawing:

<table>
    <tr>
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/4_color_change_cyan_stroke.png" width="200"/>
        </td>  
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/5_color_change_magenta_stroke.png" width="200"/>
        </td>  
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/6_color_change_yellow_stroke.png" width="200"/>
        </td>  
    </tr>
</table>
4. Erase (with eraser button or context menu):

<table>
    <tr>
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/7_erase_action.png" width="200"/>
        </td>  
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/8_erase_from_context_menu.png" width="200"/>
        </td>  
        <td>    
            <img src="https://github.com/RobertNeat/Drawing_app/blob/main/res_images/9_after_erase.png" width="200"/>
        </td>  
    </tr>
</table>
