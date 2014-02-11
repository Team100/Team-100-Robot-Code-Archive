#include <nds.h>
#include <stdio.h>
#include "PCXConvert.h"

int Team100loadPCX(const unsigned char* pcx, u32 pcxSize, sImage* image)
{
	int i;
	int j;
	u16 color;
	u8 cnt;
	u8 curPCXVal;
	bool lineDone;
	int pixelCnt;
	pPCXHeader pHdr;
	u8 * pPCXdata;
	u16 * pData;
	u16 * pScratchpad;
	u16 * pImageLine;
	bool usePalette;
	u8 *pPalette;
	
	
	pHdr = (pPCXHeader) pcx;
	pPCXdata = ((u8 *)pcx) + 128;
	pPalette = (u8*) pcx + pcxSize - 768;
	
	if ((pHdr -> version == 5) &&
		(pHdr -> bitsPerPixel == 8))
	{
		if (pHdr -> colorPlanes == 1)
		{
			/* expecting a 256 * 3 color palette at end of file */
			if ((pcxSize < 759) || (pcx[pcxSize - 769] != 0x0C))
			{
				image -> image.data16 = NULL;
				return (0);
			}
			else
			{
				usePalette = true;
			}
		}
		else
		{
			usePalette = false;
		}
		/* Determine how much memory we need to allocate for the image */
		image -> height = (pHdr -> ymax - pHdr -> ymin) + 1;
		image -> width = (pHdr -> xmax - pHdr -> xmin) + 1;
		image -> bpp = 16;
		image -> image . data8 = (u8*) malloc (image -> width * image -> height * 2);
		pScratchpad = (u16*) malloc (pHdr -> bytesPerLine * 2);
		if ((image -> image.data8) && (pScratchpad))
		{
			pImageLine = image -> image . data16;
			for (i = 0; i < image -> height; i ++)
			{
				/* Decode one line */
				pData = pScratchpad;
				for (j = 0; j < pHdr -> bytesPerLine; j ++)
				{
					*(pData ++) = 0x8000;	
				}
				lineDone = false;
				pixelCnt = 0;
				color = pHdr -> colorPlanes - 1;
				pData = pScratchpad;
				while (!lineDone)
				{
					cnt = 1;
					curPCXVal = *(pPCXdata ++);
					if ((curPCXVal & 0xC0) == 0xc0)
					{
						cnt = curPCXVal & 0x3F;
						curPCXVal = *(pPCXdata ++);
					}
					while (cnt > 0) 
					{
						cnt --;
						pixelCnt ++;
						if (usePalette)
						{
							*(pData++)	|= ((((u16)pPalette[((u16)curPCXVal * 3) + 0] >> 3) << 0) |
										    (((u16)pPalette[((u16)curPCXVal * 3) + 1] >> 3) << 5) |
										    (((u16)pPalette[((u16)curPCXVal * 3) + 2] >> 3) << 10));
						}
						else
						{
							*(pData++)	|= (((u16)curPCXVal >> 3) << ((pHdr -> colorPlanes -1 - color) * 5));
						}

						if ((pixelCnt % pHdr->bytesPerLine) == 0)
						{
							if (color) 
							{
								color --;
								pData = pScratchpad;	
							} 
							else 
							{
								/* Flush the Scratchpad cache to ensure proper DMA */
								DC_FlushRange (pScratchpad, pHdr -> bytesPerLine * 2);
								dmaCopy (pScratchpad, pImageLine, image->width * 2);
								pImageLine += image->width;
								lineDone = true;
							}
						}
					}
				}
			}
			free (pScratchpad);
			return (1);
		}
		else
		{
			if (image -> image.data8) {free (image -> image.data8);}
			if (pScratchpad) {free (pScratchpad);}
			image -> image.data16 = NULL;
			return (0);
		}
	}
	else
	{
		image -> image.data16 = NULL;
		return (0);
	}
}