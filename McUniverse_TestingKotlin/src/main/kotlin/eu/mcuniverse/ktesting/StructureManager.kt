package eu.mcuniverse.ktesting;

import java.io.File
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader
import java.io.FileInputStream
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import org.bukkit.Location
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.function.operation.Operations

class StructureManager {

	companion object {

		fun loadSchematic(loc: Location, path: String, fileName: String) {
			val file: File = File(path + "/" + fileName);

			val format: ClipboardFormat? = ClipboardFormats.findByFile(file);
			var reader: ClipboardReader? = format!!.getReader(FileInputStream(file));
			reader.use {
				var clipboard: Clipboard = reader!!.read();

				var editSession: EditSession? = WorldEdit.getInstance().getEditSessionFactory()
					.getEditSession(BukkitAdapter.adapt(loc.world), -1);
				try {
					val operation: Operation = ClipboardHolder(clipboard).createPaste(editSession)
											.to(BlockVector3.at(loc.blockX, loc.blockY + 1, loc.blockZ))
											.ignoreAirBlocks(false).build();
					Operations.complete(operation);
				} finally {
					if (editSession != null) editSession.close();
				}
			}

		}

	}

}