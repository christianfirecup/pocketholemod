package net.mcreator.pocketdimentions.procedures;

import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Util;
import net.minecraft.util.Rotation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Mirror;
import net.minecraft.state.Property;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;

import net.mcreator.pocketdimentions.block.KeyblockBlock;
import net.mcreator.pocketdimentions.PocketDimentionsModVariables;
import net.mcreator.pocketdimentions.PocketDimentionsMod;

import java.util.Map;
import java.util.Collections;

public class PocketPlayerEntersDimensionProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency world for procedure PocketPlayerEntersDimension!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				PocketDimentionsMod.LOGGER.warn("Failed to load dependency entity for procedure PocketPlayerEntersDimension!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		Entity entity = (Entity) dependencies.get("entity");
		if ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new PocketDimentionsModVariables.PlayerVariables())).timer == true) {
			if (PocketDimentionsModVariables.WorldVariables.get(world).firstenter == false) {
				PocketDimentionsModVariables.WorldVariables.get(world).firstenter = (boolean) (true);
				PocketDimentionsModVariables.WorldVariables.get(world).syncData(world);
				if (world instanceof ServerWorld) {
					Template template = ((ServerWorld) world).getStructureTemplateManager()
							.getTemplateDefaulted(new ResourceLocation("pocket_dimentions", "nexustest"));
					if (template != null) {
						template.func_237144_a_((ServerWorld) world, new BlockPos((int) (-7), (int) 0, (int) (-7)), new PlacementSettings()
								.setRotation(Rotation.COUNTERCLOCKWISE_90).setMirror(Mirror.FRONT_BACK).setChunk(null).setIgnoreEntities(false),
								((World) world).rand);
					}
				}
				{
					BlockPos _bp = new BlockPos((int) 1, (int) 1, (int) 1);
					BlockState _bs = KeyblockBlock.block.getDefaultState();
					BlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
						Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
						if (_property != null && _bs.get(_property) != null)
							try {
								_bs = _bs.with(_property, (Comparable) entry.getValue());
							} catch (Exception e) {
							}
					}
					world.setBlockState(_bp, _bs, 3);
				}
				{
					Entity _ent = entity;
					_ent.setPositionAndUpdate(0, 2, 0);
					if (_ent instanceof ServerPlayerEntity) {
						((ServerPlayerEntity) _ent).connection.setPlayerLocation(0, 2, 0, _ent.rotationYaw, _ent.rotationPitch,
								Collections.emptySet());
					}
				}
				{
					boolean _setval = (boolean) (false);
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.timer = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
			if (PocketDimentionsModVariables.WorldVariables.get(world).firstenter == true) {
				{
					Entity _ent = entity;
					_ent.setPositionAndUpdate(0, 5, 0);
					if (_ent instanceof ServerPlayerEntity) {
						((ServerPlayerEntity) _ent).connection.setPlayerLocation(0, 5, 0, _ent.rotationYaw, _ent.rotationPitch,
								Collections.emptySet());
					}
				}
				if (!world.isRemote()) {
					MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
					if (mcserv != null)
						mcserv.getPlayerList().func_232641_a_(new StringTextComponent("wellcome back"), ChatType.SYSTEM, Util.DUMMY_UUID);
				}
			}
		} else if ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new PocketDimentionsModVariables.PlayerVariables())).timer == false) {
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Please wait 20 seconds"), (true));
			}
			while ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new PocketDimentionsModVariables.PlayerVariables())).number > 0) {
				new Object() {
					private int ticks = 0;
					private float waitTicks;
					private IWorld world;

					public void start(IWorld world, int waitTicks) {
						this.waitTicks = waitTicks;
						MinecraftForge.EVENT_BUS.register(this);
						this.world = world;
					}

					@SubscribeEvent
					public void tick(TickEvent.ServerTickEvent event) {
						if (event.phase == TickEvent.Phase.END) {
							this.ticks += 1;
							if (this.ticks >= this.waitTicks)
								run();
						}
					}

					private void run() {
						{
							double _setval = (double) ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new PocketDimentionsModVariables.PlayerVariables())).number - 1);
							entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.number = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 20);
			}
			if ((entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new PocketDimentionsModVariables.PlayerVariables())).number == 0) {
				{
					double _setval = (double) 20;
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.number = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = (boolean) (true);
					entity.getCapability(PocketDimentionsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.timer = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}
