#include <nds.h>
#include <fat.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>

#include "graphics.h"

Sprite sprites [SC_MAX_CTRLS] = {
	{"tab", 64, 32, 2, tabBitmap},
	{"auto", 64, 32, 2, autoBitmap},
	{"box", 16, 16, 2, boxBitmap},
	{"down", 16, 8, 1, downBitmap},
	{"end",  64, 32, 2, endBitmap},
	{"finished", 64, 32, 1, finishedBitmap},
	{"init", 64, 32, 2, initBitmap},
	{"line", 64, 8, 1, lineBitmap },
	{"minus", 10, 8, 1, minusBitmap},
	{"numbers", 16, 16, 49, numbersBitmap},
	{"numbers2", 8, 8, 10, numbers2Bitmap},
	{"rating", 16, 16, 2, ratingBitmap},
	{"slider", 64, 8, 19, sliderBitmap },
	{"tele", 64, 32, 2, teleBitmap},
	{"up", 16, 8, 1, upBitmap}
};

//---------------------------------------------------------------------------------
void initVideo (PrintConsole * pTopConsole, 
			    PrintConsole *pBotConsole, 
				int * pTopBg,
				int * pBotBg)
//---------------------------------------------------------------------------------
{
	videoSetMode(MODE_5_2D);
	videoSetModeSub(MODE_5_2D);

	vramSetBankA(VRAM_A_MAIN_BG);
	vramSetBankC(VRAM_C_SUB_BG);
	
	/* For the sub display, only 128K of VRAM (VRAM_C) can be allocated for all backgrounds.
	   This memory must also be shared by all the backgrounds on the sub display.
	   We're using video mode MODE_5_2D with 
	     Background 0 for the console (text printing) - uses memory location 0x6200000 - 0x6201FFF
			BgType_Text 4bpp - text 4 bits per pixel 128 char * 64 pixels/char * .5 bytes/pixel = 4K tile memory
			BgSize_T_256x256 - 32x32x2 bytes = 2048 bytes (2K map memory)
		 Background 3 for the 16-bit color bitmapped. Only allows for 256x192 bit map
		    uses memory location -x6208000 - 0x621FFFF
			BgType_Bmp16 = 16-bit color bitmap
			BgSize_B16_256x256 - only use 256x192 pixels = 256x192x2 bytes = 96K
			map offset = 2 = 32K ( map offset is in terms of 16K chunks)
	*/
	consoleInit(pTopConsole, 0,BgType_Text4bpp, BgSize_T_256x256, 3, 0, true, true);
	consoleInit(pBotConsole, 0,BgType_Text4bpp, BgSize_T_256x256,3, 0, false, true);
	*(pBotBg) = bgInitSub (3,  BgType_Bmp16, BgSize_B16_256x256, 2, 0);
	bgShow (*(pBotBg));
	*(pTopBg) = bgInit (3, BgType_Bmp16, BgSize_B16_256x256, 2, 0);
	bgShow (*(pTopBg));
}

//---------------------------------------------------------------------------------
void renderColorRect (int BgID, 
					int x,
					int y,
					int width,
					int height,
					u16 color)
//---------------------------------------------------------------------------------
{
	int i;
	u16* mygfxptr;
	
	/* Fill in solid rectangle */
	mygfxptr =  bgGetGfxPtr  (BgID);
	mygfxptr += (x + (y  *SCREEN_WIDTH));

	for (i = 0; i < height; i ++)
	{
		dmaFillHalfWords (color, mygfxptr, width*2); 
		mygfxptr += SCREEN_WIDTH;
	}
}


//---------------------------------------------------------------------------------
void renderControl (int BgID,
					int x,
					int y,
					int frame,
					Sprite *ptrCtrl)
//---------------------------------------------------------------------------------
{		
	int i;
	u16* mygfxptr;

	mygfxptr =  bgGetGfxPtr  (BgID);
	mygfxptr += (x + (y * SCREEN_WIDTH));
	for(i=0; i < ptrCtrl -> height; i++)
	{
		dmaCopy (&ptrCtrl -> BitmapData[(i + (ptrCtrl -> height * frame)) * ptrCtrl -> width],
				mygfxptr, 
				ptrCtrl-> width*2);
		mygfxptr += SCREEN_WIDTH;
	}

}


//---------------------------------------------------------------------------------
void renderImage (int BgID,
				  int x,
				  int y,
				  sImage *pimage)
//---------------------------------------------------------------------------------
{ 
	int i;
	u16* mygfxptr;

	mygfxptr =  bgGetGfxPtr  (BgID);
	mygfxptr += (x + (y * SCREEN_WIDTH));
	for(i=0; i <pimage -> height; i++)
	{
		dmaCopy (&pimage -> image.data16[(i * pimage -> width)], 
				mygfxptr, 
				pimage-> width*2);
		mygfxptr += SCREEN_WIDTH;
	}


}

//---------------------------------------------------------------------------------
bool renderPCX (int BgID, int x, int y, char * fileName)
//---------------------------------------------------------------------------------
{
	FILE * imgFile;
	sImage image;

	imgFile= fopen (fileName, "r");
	
	if (imgFile)
	{
		u32 imgSize;
		char * buffer;

		fseek (imgFile, 0, SEEK_END);
		imgSize = ftell (imgFile);
		rewind (imgFile);
		
		buffer = (char*) malloc (imgSize);
		fread (buffer, 1, imgSize, imgFile);
		
		// close the file
		fclose (imgFile);
	
		if (Team100loadPCX((const unsigned char*)buffer, imgSize, &image))
		{
			renderImage (BgID, x, y, &image);
			free (image.image.data16);
		}
		free ((void *)buffer);
		//printf(" ");
		return (true);
	}
	else
	{
		return (false);
	}
}
